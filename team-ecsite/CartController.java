package jp.co.internous.kabuki.controller;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.google.gson.Gson;

import jp.co.internous.kabuki.model.domain.TblCart;
import jp.co.internous.kabuki.model.domain.dto.CartDto;
import jp.co.internous.kabuki.model.form.CartForm;
import jp.co.internous.kabuki.model.mapper.TblCartMapper;
import jp.co.internous.kabuki.model.session.LoginSession;

@Controller
@RequestMapping("/kabuki/cart")
public class CartController {

	@Autowired
	private TblCartMapper cartMapper;

	@Autowired
	private LoginSession loginSession;

	private Gson gson = new Gson();

	@RequestMapping("/")
	public String index(Model m) {
		// ユーザーIDを取得
		long userId = loginSession.getIsLogin() ? loginSession.getUserId() : loginSession.getTemporaryUserId();

		// カート情報を取得
		List<CartDto> carts = cartMapper.findByUserId(userId);

		// page_header.htmlでsessionの変数を表示させているため、loginSessionも画面に送る。
		m.addAttribute("loginSession", loginSession);
		m.addAttribute("carts", carts);
		return "cart";
	}

	@RequestMapping("/add")
	public String addCart(CartForm f, Model m) {

		// ユーザーIDを取得
		long userId = loginSession.getIsLogin() ? loginSession.getUserId() : loginSession.getTemporaryUserId();

		f.setUserId(userId);

		// カートテーブルに挿入/更新
		TblCart cart = new TblCart(f);
		long result = 0;
		if (cartMapper.findCountByUserIdAndProuductId(userId, f.getProductId()) > 0) {
			result = cartMapper.update(cart);
		} else {
			result = cartMapper.insert(cart);
		}
		if (result > 0) {
			List<CartDto> carts = cartMapper.findByUserId(userId);

			m.addAttribute("loginSession", loginSession);
			m.addAttribute("carts", carts);
		}
		return "cart";
	}

	@SuppressWarnings("unchecked")
	@RequestMapping("/delete")
	@ResponseBody
	public boolean deleteCart(@RequestBody String checkedIdList) {
		long result = 0;

		Map<String, List<String>> map = gson.fromJson(checkedIdList, Map.class);
		List<String> checkedIds = map.get("checkedIdList");

		result = cartMapper.deleteById(checkedIds);
		return result > 0;
	}
}