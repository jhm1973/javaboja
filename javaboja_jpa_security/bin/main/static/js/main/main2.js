//요청 페이지 크기
var pageSize=10;
//요청 url
var url="https://dapi.kakao.com/v2/local/search/keyword.json?";
	$(document).ready(function(){
		
		placeDetail = function(id){
			$("#paging_button").css("display","none");
			$("#search_result").css("display","none");
			$("#history_result").css("display","none");
			$("#popular_result").css("display","none");
			$.get("/main/place/detail",
				{ id : id
				},
				function(data,status){
					console.log(data);
					var detail_text='<div> 이름 : '+data.placeName+'</div>'+
									'<div> 구주소 : '+data.addressName+'</div>'+
									'<div> 도로명주소 : '+data.roadAddressName+'</div>'+
									'<div> URL : '+data.placeUrl+'</div>'+
									'<div> 전화번호 : '+data.phone+'</div>'+
									'<a href="https://map.kakao.com/link/map/'+data.placeId+'">'+'지도 바로가기</a>'+
									'<div id="kk_map"></div>';
					$("#search_detail").html(detail_text);
					$("#search_detail").css("display","inline");
					var mapContainer = document.getElementById('kk_map'), // 지도의 중심좌표
				    mapOption = { 
				        center: new kakao.maps.LatLng(data.latitude, data.longitude), // 지도의 중심좌표
				        level: 3 // 지도의 확대 레벨
				    }; 

					var map = new kakao.maps.Map(mapContainer, mapOption); // 지도를 생성합니다
					// 마커를 표시할 위치입니다 
					var position =  new kakao.maps.LatLng(data.latitude, data.longitude);

					// 마커를 생성합니다
					var marker = new kakao.maps.Marker({
						map: map,
			            position: position
					});
					// 마커를 지도에 표시합니다.
					marker.setMap(map);
					var content = '<div class="kk_wrap">'+
			        ' <div class="kk_info">'+
		           ' <div class="kk_title">'+data.placeName+
		           '     <div class="kk_close" onclick="closeOverlay()" title="닫기"></div>'+
		           ' </div>'+
		           ' <div class="kk_body">'+
		           '  <div class="kk_img"> <img src="http://cfile181.uf.daum.net/image/250649365602043421936D" width="73" height="70">'+
		              '  </div>'+
		               ' <div class="kk_desc">'+
		                  '  <div class="kk_ellipsis">'+data.roadAddressName+'</div>'+
		               ' </div>'+
		            '</div>'+
			       ' </div>'+
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
				}
			);
		}
		
		pageMove = function(pageNum){
			//검색 키워드
			var keyword=$("#search_val").val();
			ajax_get_search(keyword, pageNum, url);
		}
		ajax_get_search = function(keyword, curPage, url){
			$("#search_detail").css("display","none");
			$("#history_result").css("display","none");
			$("#paging_button").css("display","none");
			$("#popular_result").css("display","none");
			$.get("/main/place/search",
				{ keyword : keyword,
				  curPage : curPage,
				  pageSize : pageSize,
				  url : url
				},
				function(data, status){
					console.log(data);
					var jsonData = JSON.parse(data);
					var is_end = jsonData.meta.is_end;
					var total_count = jsonData.meta.total_count;
					var total_page=Math.ceil(total_count/pageSize);
					var paging_num=Math.floor(curPage/(pageSize+1))*pageSize+1;
					var result_data = jsonData.documents;
					$("#curPage").val(curPage)
					$("#totalPage").val(total_page)
					console.log(jsonData);
						if(is_end==true && total_count==0){
							alert("검색 결과가 없습니다.");
							return;
						}else{
							if(total_page>1){		
								
								/*if(curPage>1){
									$("#first_page").removeClass("disabled");
								}
								if(total_page>curPage){
									$("#last_page").removeClass("disabled");
								}
								if(curPage==total_count){
									$("#last_page").addClass("disabled");
								}
								
								if(paging_num<total_page){
									$("#next_page").removeClass("disabled");
								}else{
									$("#next_page").addClass("disabled");
								}
								
								if(paging_num>1){
									$("#pre_page").removeClass("disabled");
								}else{
									$("#pre_page").addClass("disabled");
								}*/
									//start, end page 번호 추출
								var curRange = Math.floor((curPage-1)/pageSize)+1;
								var startPage = Math.floor((curRange-1)*pageSize)+1;
								var endPage = startPage + pageSize-1;
								console.log("pageSize : "+pageSize);
								console.log("total_page : "+total_page);
								if(endPage>total_page){
									endPage = total_page;
								}
									/*var startPage;
									if(curPage%pageSize==0){
										startPage = (Math.floor(curPage/pageSize)-1)*pageSize+1;
									}else{
										startPage = (Math.floor(curPage/pageSize))*pageSize+1;
									}
									
									var endPage;
									if(startPage+pageSize>=total_page){
										endPage = total_page;
									}else{
										endPage = startPage+pageSize;
									}*/
									//태그 초기화
									$(".pageNumber").each(function(index, item){
										$(item).remove();
									})
									$(".divTableBody").each(function(index, item){
										$(item).remove();
									})
								var page_text='';
								for(var i=startPage;i<=endPage;i++){
									if(i==curPage){
										page_text = page_text+'<li class="active pageNumber"><a href="#" onclick="pageMove('+i+');">'+i+'</a></li>'
									}else{
										page_text = page_text+'<li class="pageNumber"><a href="#" onclick="pageMove('+i+');">'+i+'</a></li>'
									}
									
								}
								var result_text='';
								$.each(result_data,function(key,value){
									console.log("key : "+key+", value : "+value.place_name);
									result_text=result_text+"<div class='divTableBody'>"+
												"<div class='divTableRow'>"+
												"<div class='divTableCell'>"+
												"<a onclick='placeDetail("+value.id+");'>"+value.place_name+"</a>"+
												"</div>"+
												"</div>"+
												"</div>";		
								});
							}
						}
						$("#pre_page").after(page_text);
						$(".divTableHeading").after(result_text);
						$("#paging_button").css("display","inline");
						$("#search_result").css("display","inline");
				}
			);
		}
		$("#search_go").on("click",function(){
			//검색 키워드
			$("#page_info").val("search");
			var keyword=$("#search_val").val();
			ajax_get_search(keyword, 1, url);
		});
		
		$("#first_page").on("click",function(){
			//검색 키워드
			var keyword=$("#search_val").val();
			//페이지 정보
			var page_info=$("#page_info").val();
			
			if(page_info=="search"){
				
				ajax_get_search(keyword, 1, url);
			}else if(page_info=="history"){
				ajax_get_history(1);
			}else if(page_info=="popular"){
				ajax_get_history(1);
			}
			
		});
		$("#pre_page").on("click",function(){
			//검색 키워드
			var keyword=$("#search_val").val();
			//현재 페이지
			var curPage=$("#curPage").val();
			//마지막 페이지
			var lastpage=$("#totalPage").val();
			//페이지 정보
			var page_info=$("#page_info").val();
			if(curPage==1){
				alert("첫번째 페이지 입니다.");
				return;
			}
			if(page_info=="search"){		
				ajax_get_search(keyword, Number(curPage)-1, url);
			}else if(page_info=="history"){
				ajax_get_history(Number(curPage)-1);
			}else if(page_info=="popular"){
				ajax_get_history(Number(curPage)-1);
			}
			
		});
		$("#next_page").on("click",function(){
			//검색 키워드
			var keyword=$("#search_val").val();
			//현재 페이지
			var curPage=$("#curPage").val();
			//마지막 페이지
			var lastpage=$("#totalPage").val();
			//페이지 정보
			var page_info=$("#page_info").val();
			if(curPage==lastpage){
				alert("마지막 페이지 입니다.");
				return;
			}
			if(page_info=="search"){		
				ajax_get_search(keyword, Number(curPage)+1, url);
			}else if(page_info=="history"){
				ajax_get_history(Number(curPage)+1);
			}else if(page_info=="popular"){
				ajax_get_history(Number(curPage)+1);
			}
		});
		$("#last_page").on("click",function(){
			//검색 키워드
			var keyword=$("#search_val").val();
			//현재 페이지
			var curPage=$("#curPage").val();
			//마지막 페이지
			var lastpage=$("#totalPage").val();
			//페이지 정보
			var page_info=$("#page_info").val();
			
			alert(lastpage);
			if(page_info=="search"){		
				ajax_get_search(keyword, lastpage, url);
			}else if(page_info=="history"){
				ajax_get_history(lastpage);
			}else if(page_info=="popular"){
				ajax_get_history(lastpage);
			}
		});
		/*$(".active").on("click",function(){
			//검색 키워드
			var keyword=$("#search_val").val();
			alert(this.$("a").val());
		});*/
		ajax_get_popular = function(curPage){
			
			$.get("/main/popular",
				function(data,status){
					console.log(data);
					var result_text='';
					$.each(data,function(key,value){
						console.log("key : "+key+", value : "+value.place_name);
						result_text=result_text+"<div class='divTableBody'>"+
													"<div class='divTableRow'>"+
														"<div class='divTableCell'>"+(Number(key)+1)+"</div>"+
														"<div class='divTableCell'>"+value.keyword+"</div>"+
														"<div class='divTableCell'>"+value.createDateTime+"</div>"+
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
					console.log(data);
					var total_count = data.totalElements;
					var pageSize = data.size;
					var curPage = data.number+1;
					var paging_num=Math.floor(curPage/(pageSize+1))*pageSize+1;
					var result_data = data.content;
					var total_page=Math.ceil(total_count/pageSize);
					console.log("total_page : "+total_page);
					$("#curPage").val(curPage);
					$("#totalPage").val(total_page);
					if(total_page>1){
						//start, end page 번호 추출
						var curRange = Math.floor((curPage-1)/pageSize)+1;
						var startPage = Math.floor((curRange-1)*pageSize)+1;
						console.log("curPage : "+curPage);
						/*if(curPage%pageSize==0){
							startPage = (Math.floor(curPage/pageSize)-1)*pageSize+1;
						}else{
							startPage = (Math.floor(curPage/pageSize))*pageSize+1;
						}*/
						
						var endPage = startPage + pageSize-1;
						console.log("pageSize : "+pageSize);
						console.log("total_page : "+total_page);
						if(endPage>total_page){
							endPage = total_page;
						}/*else{
							endPage = startPage+pageSize;
						}*/
						console.log("startpage : "+startPage);
						console.log("endPage : "+endPage);
						//태그 초기화
						$(".pageNumber").each(function(index, item){
							$(item).remove();
						})
						$(".divTableBody").each(function(index, item){
							$(item).remove();
						})
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
					console.log(page_text);
					$("#pre_page").after(page_text);
					$(".divTableHeading").after(result_text);
					$("#paging_button").css("display","inline");
					$("#history_result").css("display","inline");
					}
				}
			);
		}
		$("#history").on("click",function(){
			$("#search_result").css("display","none");
			$("#search_detail").css("display","none");
			$("#paging_button").css("display","none");
			$("#popular_result").css("display","none");
			ajax_get_history(1);
		});
		$("#popular").on("click",function(){
			$("#search_result").css("display","none");
			$("#search_detail").css("display","none");
			$("#paging_button").css("display","none");
			$("#history_result").css("display","none");
			ajax_get_popular(1);
		});
		
		
	});
	/**
 * 
 */