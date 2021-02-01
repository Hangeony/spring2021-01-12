<%@ page language="java" contentType="text/html; charset=UTF-8"
  pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/4.5.2/css/bootstrap.min.css">
<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.5.1/jquery.min.js"></script>
<script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.16.0/umd/popper.min.js"></script>
<script src="https://maxcdn.bootstrapcdn.com/bootstrap/4.5.2/js/bootstrap.min.js"></script>
<script src="https://kit.fontawesome.com/a076d05399.js"></script>

<title>AJAX Ex3</title>
</head>
<body>
<h1>AJAX Ex3</h1>
<div>
<button id ="btn-1">댓글 등록성공</button>
</div>
<div>
<button id ="btn-2">댓글 등록실패</button>
</div>
<div>
<button id ="btn-3">댓글 등록 성공 or 실패</button>
</div>
<div>
<button id ="btn-4">댓글 목록</button>
</div>
<div>
<button id ="btn-5">댓글 수정</button>
</div>
<div>
<button id ="btn-6">댓글 삭제</button>
</div>
<script>
$(document).ready(function() {
	$("#btn-1").click(function() {
		$.ajax({
			method: "POST",
			url : "/controller/replies/new",
			data : '{"bno":165,"reply":"새 댓글입니다.", "replyer":"user03"}',
			contentType: "application/json",
			complete : function(jqXHR, status){
				console.log("complete");
				console.log(status);
			}
		}); 
	});
	$("#btn-2").click(function() {
		$.ajax({
			method: "POST",
			url : "/controller/replies/new",
			data : '{"reply":"새 댓글입니다.", "replyer":"user03"}',
			contentType: "application/json",
			complete : function(jqXHR, status){
				console.log("complete");
				console.log(status);
			}
		}); 
	});
	$("#btn-3").click(function() {
		$.ajax({
			type: "post",
			url: "/controller/replies/new",
			contentType: "application/json",
			data: '{"bno":165,"reply":"새 댓글~","replyer":"user01"}',
			success: function(data, status, xhr) {
				console.log("등록 성공");
				// console.log(jqXHR.responseText);
				console.log(data);
			},
			error: function() {
				console.log("등록 실패");
			}
		});
	});
	$("#btn-4").click(function(){
		$.ajax({
			url : "/controller/replies/pages/165/1",
			type : "get",
			success: function(data, status, xhr){
				console.log(data);
			}
		});
	});
	$("#btn-5").click(function(){
		$.ajax({
			type : "put",
			url : "/controller/replies/39",
			data : '{"bno":165,"reply":"3차 수정함"}',
			contentType : "application/json",
			success : function(data, status, xhr){
				console.log("수정성공");
				console.log(data);
			},
			error : function(){
				console.log("수정실패");
				}
		});
	});
	$("#btn-6").click(function(){
		$.ajax({
			type : "DELETE" ,
			url : "/controller/replies/37",
			success : function(data, status, xhr){
				console.log("삭제되었습니다.");
				console.log(data);
			},
			error : function(){
				console.log("삭제 되었거나 존재하지 않는 글 입니다.");
			}
		});
	})
});
</script>
</body>
</html>