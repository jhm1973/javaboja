//요청 페이지 크기
var pageSize=10;
//요청 url
var url="https://dapi.kakao.com/v2/local/search/keyword.json?";
$(document).ready(function(){
	
	display_none_set = function() {
		$("#search_detail").css("display","none");
		$("#history_result").css("display","none");
		$("#paging_button").css("display","none");
		$("#search_result").css("display","none");
		$("#popular_result").css("display","none");
	}

	pageClickFn = function(type){
		//검색 키워드
		var keyword=$("#search_val").val();
		//현재 페이지
		var curPage=$("#curPage").val();
		//마지막 페이지
		var lastpage=$("#totalPage").val();
		//페이지 정보
		var page_info=$("#page_info").val();
		
		if(type=="first_page" || type=="pre_page"){
			if(curPage==1){
				alert("첫번째 페이지 입니다.");
				return;
			}
		}
		if(type=="next_page" || type=="last_page"){
			if(curPage==lastpage){
				alert("마지막 페이지 입니다.");
				return;
			}
		}
		
		if(page_info=="search"){
			if(type=="first_page"){
				
				ajaxGetSearch(keyword, 1, url, false);
			}else if(type=="pre_page"){
				ajaxGetSearch(keyword, Number(curPage)-1, url, false);
			}else if(type=="next_page"){
				
				ajaxGetSearch(keyword, Number(curPage)+1, url, false);
			}else if(type=="last_page"){
				ajaxGetSearch(keyword, lastpage, url, false);
			}
		}else if(page_info=="history"){
			if(type=="first_page"){
				ajax_get_history(1);
			}else if(type=="pre_page"){
				ajax_get_history(Number(curPage)-1);
			}else if(type=="next_page"){
				ajax_get_history(Number(curPage)+1);
			}else if(type=="last_page"){
				ajax_get_history(lastpage);
			}
		}
	}

	var ajax_paging = function(data){
		display_none_set();
		var total_count = data.totalElements;
		var pageSize = data.size;
		var curPage = data.number+1;
		var paging_num=Math.floor(curPage/(pageSize+1))*pageSize+1;
		var resultData = data.content;
		var total_page=Math.ceil(total_count/pageSize);
		console.log("현재 페이지 : "+curPage);
		console.log("현재 페이지 : "+total_page);
		$("#curPage").val(curPage);
		$("#totalPage").val(total_page);
		//start, end page 번호 추출
		var curRange = Math.floor((curPage-1)/pageSize)+1;
		var startPage = Math.floor((curRange-1)*pageSize)+1;
		var endPage = startPage + pageSize-1;
		if(endPage>total_page){
			endPage = total_page;
		}
		//태그 초기화
		$(".pageNumber").each(function(index, item){
			$(item).remove();
		})
		$(".divTableBody").each(function(index, item){
			$(item).remove();
		})
		return {
			startPage : startPage,
			endPage : endPage,
			resultData : resultData,
			
		}
	}

	placeDetail = function(id, keyword, url, pageSize, curPage){
		display_none_set();
		$.get("/main/place/detail",
			{ id : id,
			  keyword : keyword,
			  url : url,
			  pageSize : pageSize,
			  curPage : curPage
			},
			function(data,status){
					data = JSON.parse(data);
					var detail_text='<div> 이름 : '+data.place_name+'</div>'+
					'<div> 구주소 : '+data.address_name+'</div>'+
					'<div> 도로명주소 : '+data.roadAddress_name+'</div>'+
					'<div> URL : '+data.place_url+'</div>'+
					'<div> 전화번호 : '+data.phone+'</div>'+
					'<a href="https://map.kakao.com/link/map/'+data.id+'">'+'지도 바로가기</a>'+
					'<div id="kk_map"></div>';
					$("#search_detail").html(detail_text);
					$("#search_detail").css("display","inline");
					var mapContainer = document.getElementById('kk_map'), // 지도의 중심좌표
				    mapOption = { 
				        center: new kakao.maps.LatLng(data.y, data.x), // 지도의 중심좌표
				        level: 3 // 지도의 확대 레벨
				    }; 
			
					var map = new kakao.maps.Map(mapContainer, mapOption); // 지도를 생성합니다
					// 마커를 표시할 위치입니다 
					var position =  new kakao.maps.LatLng(data.y, data.x);
			
					// 마커를 생성합니다
					var marker = new kakao.maps.Marker({
						map: map,
			            position: position
					});
					// 마커를 지도에 표시합니다.
					marker.setMap(map);
					var content = '<div class="kk_wrap">'+
			        			  	'<div class="kk_info">'+
			        			  		'<div class="kk_title">'+data.place_name+
			        			  			'<div class="kk_close" onclick="closeOverlay()" title="닫기"></div>'+
			        			  		'</div>'+
			        			  		'<div class="kk_body">'+
			        			  			'<div class="kk_img"> <img src="http://cfile181.uf.daum.net/image/250649365602043421936D" width="73" height="70">'+
			        			  			'</div>'+
			        			  			'<div class="kk_desc">'+
			        			  				'<div class="kk_ellipsis">'+data.road_address_name+'</div>'+
			        			  			'</div>'+
			        			  		'</div>'+
			        			  	'</div>'+
			        			  '</div>';
					// 마커 위에 커스텀오버레이를 표시합니다
					// 마커를 중심으로 커스텀 오버레이를 표시하기위해 CSS를 이용해 위치를 설정했습니다
					var overlay = new kakao.maps.CustomOverlay({
					    content: content,
					    map: map,
					    position: marker.getPosition()       
					});
					// 마커를 클릭했을 때 커스텀 오버레이를 표시합니다
					kakao.maps.event.addListener(marker, 'click', function() {
					    overlay.setMap(map);
					});
					closeOverlay = function(){
						overlay.setMap(null);     
					}
					var state = {'page_id':1,'user_id':5};
					var title = "";
					history.pushState(state,title);
			}
			
		);
	}

	pageMove = function(pageNum){
		//조회수 증가 여부
		var realSearch=false;
		//검색 키워드
		var keyword=$("#search_val").val();
		ajaxGetSearch(keyword, pageNum, url, realSearch);
	}
	
	ajaxGetSearch = function(keyword, curPage, url, realSearch){
		$.get("/main/place/search",
			{ keyword : keyword,
			  curPage : curPage,
			  pageSize : pageSize,
			  url : url,
			  realSearch : realSearch
			},
			function(data, status){
				var jsonData = JSON.parse(data);
				console.log(jsonData.documents);
				var paging_data = new Object();
				paging_data.totalElements = jsonData.meta.pageable_count;
				paging_data.size = pageSize;
				paging_data.number = curPage-1;
				paging_data.content = jsonData.documents;
				if(jsonData.meta.pageable_count==0){
					alert("검색 결과가 없습니다.");
					return;
				}
				
				var pagingObject = ajax_paging(paging_data);
				var startPage = pagingObject.startPage;
				var endPage = pagingObject.endPage;
				var result_data = pagingObject.resultData;
				console.log("startPage : "+startPage);
				console.log("endPage : "+endPage);
				console.log("result_data : "+result_data);
				var page_text='';
				for(var i=startPage;i<=endPage;i++){
					if(i==curPage){
						page_text = page_text+'<li class="active pageNumber"><a onclick="pageMove('+i+');">'+i+'</a></li>'
					}else{
						page_text = page_text+'<li class="pageNumber"><a onclick="pageMove('+i+');">'+i+'</a></li>'
					}
					
				}
				
				var result_text='';
				$.each(result_data,function(key,value){
					console.log("key : "+key+", value : "+value.place_name+", id : "+value.id);
					result_text=result_text+"<div class='divTableBody'>"+
								"<div class='divTableRow'>"+
								"<div class='divTableCell'>"+
								"<a onclick='placeDetail("+value.id+",\""+keyword+"\",\""+url+"\",\""+pageSize+"\",\""+curPage+"\");'>"+value.place_name+"</a>"+
								"</div>"+
								"</div>"+
								"</div>";		
				});
				$("#pre_page").after(page_text);
				$(".divTableHeading").after(result_text);
				$("#paging_button").css("display","inline");
				$("#search_result").css("display","inline");
				var state = {'page_id':curPage,'user_id':5};
				var title = "";
				console.log(state);
				history.pushState(state,null,"");	
				
			}
		);
	}
	
	ajaxGetPopular = function(){
			
			$.get("/main/popular",
			function(data,status){
				if(data.totalElements==0){
					alert("검색 결과가 없습니다.");
					return;
				}
				display_none_set();
				var result_text='';
				//태그 초기화
				$(".pageNumber").each(function(index, item){
					$(item).remove();
				})
				$(".divTableBody").each(function(index, item){
					$(item).remove();
				})
				$.each(data,function(key,value){
					console.log("key : "+key+", value : "+value);
					result_text=result_text+"<div class='divTableBody'>"+
												"<div class='divTableRow'>"+
													"<div class='divTableCell'>"+(Number(key)+1)+"</div>"+
													"<div class='divTableCell'>"+value.keyword+"</div>"+
													"<div class='divTableCell'>"+value.views+"</div>"+
												"</div>"+
											"</div>";		
				});
				$(".divTableHeading").after(result_text);
				$("#popular_result").css("display","inline");
			}
		);
	}
	
	ajax_get_history = function(curPage){
		$("#page_info").val("history");
		$.get("/main/history",
				{curPage : curPage},
			function(data,status){
				if(data.totalElements==0){
					alert("검색 결과가 없습니다.");
					return;
				}
				var pagingObject = ajax_paging(data);
				var startPage = pagingObject.startPage;
				var endPage = pagingObject.endPage;
				var result_data = pagingObject.resultData;
				
				var page_text='';
				for(var i=startPage;i<=endPage;i++){
					if(i==curPage){
						page_text = page_text+'<li class="active pageNumber"><a onclick="ajax_get_history('+i+');">'+i+'</a></li>'
					}else{
						page_text = page_text+'<li class="pageNumber"><a onclick="ajax_get_history('+i+');">'+i+'</a></li>'
					}
					
				}
				
				var result_text='';
				$.each(result_data,function(key,value){
					console.log("key : "+key+", value : "+value.place_name);
					result_text=result_text+"<div class='divTableBody'>"+
												"<div class='divTableRow'>"+
													"<div class='divTableCell'>"+(Number(Number(pageSize)*(Number(curPage)-1))+(Number(key)+1))+"</div>"+
													"<div class='divTableCell'>"+value.keyword+"</div>"+
													"<div class='divTableCell'>"+value.createDateTime+"</div>"+
												"</div>"+
											"</div>";		
				});
				$("#pre_page").after(page_text);
				$(".divTableHeading").after(result_text);
				$("#paging_button").css("display","inline");
				$("#history_result").css("display","inline");
			}
		);
	}
	
	$("#search_go").on("click",function(){
		//검색 키워드
		var keyword=$("#search_val").val();
		$("#keyword").val(keyword);
		if(keyword=='' || keyword==null){
			alert("키워드를 입력해 주세요");
			return;
		}
		//페이지 정보
		$("#page_info").val("search");
		ajaxGetSearch(keyword, 1, url, true);
	});
	
	$("#first_page").on("click",function(){
		pageClickFn($(this).attr("id"));
	});
	$("#pre_page").on("click",function(){
		pageClickFn($(this).attr("id"));
	});
	$("#next_page").on("click",function(){
		pageClickFn($(this).attr("id"));
	});
	$("#last_page").on("click",function(){
		pageClickFn($(this).attr("id"));
	});
	$("#history").on("click",function(){
		ajax_get_history(1);
	});
	$("#popular").on("click",function(){
			ajaxGetPopular();
	});
	
	window.onpopstate = function(event) {  //뒤로가기 이벤트를 캐치합니다.
		  console.log('뒤로가기 체크'); 

	}
	
});