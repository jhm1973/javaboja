package com.javaboja.controller;

import java.security.Principal;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.javaboja.dao.PlaceDao;
import com.javaboja.repository.HistoryRepository;
import com.javaboja.repository.PlaceRepository;
import com.javaboja.repository.UserRepository;
import com.javaboja.service.HistoryService;
import com.javaboja.service.PlaceService;
import com.javaboja.utils.HttpClientService;
import com.javaboja.utils.JsonConverter;
import com.javaboja.vo.History;
import com.javaboja.vo.Place;
import lombok.extern.slf4j.Slf4j;

@Controller
@Slf4j
public class MainController {

	@Autowired
	PlaceRepository placeRepository;
	@Autowired
	PlaceService placeService;
	@Autowired
	HistoryService historyService;
	@Value("${kakao.token}")
	private String kakao_token;
	
	@GetMapping("/main")
	public String main() {
		return "main";
	}
	
	@GetMapping("/main/place/search")
	@ResponseBody 
	public Page<Place> javabojaSearch(@RequestParam String keyword, 
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
	public Place javabojaSearchDetail(@RequestParam String id,
							  		Principal principal)
	{
		Place placeVo = placeRepository.findByPlaceId(id);
		return placeVo;
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
