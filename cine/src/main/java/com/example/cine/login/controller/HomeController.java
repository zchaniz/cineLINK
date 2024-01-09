package com.example.cine.login.controller;

import java.util.HashMap;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import jakarta.servlet.http.HttpSession;

@Controller
public class HomeController {
   
	KakaoAPI kakaoApi = new KakaoAPI();
	
	@RequestMapping(value="/login")
	public ModelAndView login(@RequestParam("code") String code, HttpSession session) {
		ModelAndView mav = new ModelAndView();
		// 1번 인증코드 요청 전달
		String access_token = kakaoApi.getAccessToken(code);
		// 2번 인증코드로 토큰 전달
		HashMap<String, Object> userInfo = kakaoApi.getUserInfo(access_token);
		
		System.out.println("login info : 확인 " + userInfo.toString());
		
		if(userInfo.get("kakaoemail") != null) {
			session.setAttribute("kakaoemail", userInfo.get("kakaoemail"));
			session.setAttribute("access_token", access_token);
			session.setAttribute("kakaoname", userInfo.get("kakaoname"));
		}
		
		mav.addObject("kakaoemail", userInfo.get("kakaoemail"));
		mav.addObject("kakaoname", userInfo.get("kakaoname"));
		mav.setViewName("/main/cinemain");
		return mav;
	}
// 카카오 로그인 구현
	
	@RequestMapping(value="/logout")
	public ModelAndView logout(HttpSession session) {
		ModelAndView mav = new ModelAndView();
		kakaoApi.kakaoLogout((String)session.getAttribute("accessToken"));
		session.removeAttribute("accessToken");
		session.removeAttribute("userid");
		mav.setViewName("/main/cinemain");
		return mav;
	}
	
	
	
	@GetMapping("modal")
	public String modal() { 
	      return "modal";
	   }
}






























