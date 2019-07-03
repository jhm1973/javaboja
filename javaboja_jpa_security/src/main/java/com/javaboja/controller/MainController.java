package com.javaboja.controller;

import java.security.Principal;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import com.javaboja.service.HistoryService;
import com.javaboja.service.PlaceService;
import com.javaboja.vo.History;
import lombok.extern.slf4j.Slf4j;

@Controller
@Slf4j
public class MainController {

	@Autowired
	PlaceService placeService;
	@Autowired
	HistoryService historyService;
	@Value("${kakao.token}")
	private String kakao_token;
	
	@GetMapping("/main")
	public ModelAndView main(Principal principal) {
		ModelAndView mav = new ModelAndView();
		mav.addObject("userId", principal.getName());
		mav.setViewName("main");
		return mav;
	}
	
	@GetMapping({"/*","/search*","/history*","/popular*","/search*","/detail"})
	public String mainFilter(Principal principal) {
		return "redirect:main";
	}

	@GetMapping("/main/place/search")
	@ResponseBody 
	public ResponseEntity<String> javabojaSearch(@RequestParam String keyword, 
							     @RequestParam String url,
		  					     @RequestParam String pageSize,
		  					     @RequestParam int curPage, 
		  					     @RequestParam boolean realSearch,
				  				 Principal principal) 
	{ 
		return placeService.placeSearchService(principal.getName(), keyword, url, pageSize, kakao_token, curPage, realSearch);
	}
	
	@GetMapping("/main/place/detail")
	@ResponseBody
	public ResponseEntity<String> javabojaSearchDetail(@RequestParam String id,
									  @RequestParam String keyword, 
									  @RequestParam String url,
									  @RequestParam String pageSize,
									  @RequestParam int curPage, 
							  		  Principal principal)
	{
		return placeService.placeDetail(id, keyword, url, pageSize, curPage, kakao_token);
	}
	
	@GetMapping("/main/history")
	@ResponseBody
	public Page<History> javabojaHistoryList(@RequestParam(defaultValue = "1") int curPage,
											Principal principal)
	{
		return historyService.getHistorySelect(principal.getName(), curPage);
	}
	
	@GetMapping("/main/popular")
	@ResponseBody
	public List<History> javabojaPopularList(Principal principal)
	{
		return historyService.getPopularList();
	}
}
