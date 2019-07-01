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

import com.javaboja.execution.HttpClientService;
import com.javaboja.execution.JsonConverter;
import com.javaboja.repository.HistoryRepository;
import com.javaboja.repository.PlaceRepository;
import com.javaboja.repository.UserRepository;
import com.javaboja.service.HistoryService;
import com.javaboja.service.PlaceDao;
import com.javaboja.service.PlaceService;
import com.javaboja.vo.History;
import com.javaboja.vo.Place;
import lombok.extern.slf4j.Slf4j;

@Controller
@Slf4j
public class MainController {

	@Autowired
	UserRepository userRepository;
	@Autowired
	PlaceRepository placeRepository;
	@Autowired
	HistoryRepository historyRepository;
	@Autowired
	JsonConverter jsonConverter;
	@Autowired
	PlaceService placeService;
	@Autowired
	HistoryService historyDao;
	@Value("${kakao.token}")
	private String kakao_token;
	
	@GetMapping("/main")
	public String main() {
		return "main";
	}
	
	/*@GetMapping("/main/place/search")
	@ResponseBody
	public String javabojaSearch(@RequestParam String keyword,
							  Principal principal) 
	{
		UserVo user = userRepository.findByUserId(principal.getName());
		HttpClientService hcs = new HttpClientService();
		String result = hcs.httpClientGet(keyword, url, pageSize, curPage, javaboja_token);
		//장소 정보 insert
		placeDao.placeInsert(jsonConverter.stringToJsonArray(result,"documents"));
		
		//history insert
		histioryDao.historyInsert(keyword, principal.getName());
		
		return result;	
	}*/
	
	
	@GetMapping("/main/place/search")
	@ResponseBody 
	public Page<Place> javabojaSearch(@RequestParam String keyword, 
									  @RequestParam String url,
				  					  @RequestParam String pageSize,
				  					  @RequestParam int curPage, Principal principal) 
	{ 
		return placeService.placeSearchServce(principal.getName(), keyword, url, pageSize, kakao_token, curPage);
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
		//UserVo user = userRepository.findByUserId(principal.getName());
		//int listCnt = (int)histioryDao.getHistoryCount(principal.getName());
		//PagingVo pagingVo = new PagingVo(listCnt, curPage);
		//Pageable pageable = PageRequest.of(curPage-1, pagingVo.getPageSize());
		return historyDao.getHistorySelect(principal.getName(), curPage);
	}
	
	@GetMapping("/main/popular")
	@ResponseBody
	public List<History> javabojaPopularList(Principal principal)
	{
		List<History> list = historyDao.getPopularList();
		for(int i=0;i<list.size();i++) {
			log.info("carrey : "+list.get(0).getViews());
		}
		return null;
	}
}
