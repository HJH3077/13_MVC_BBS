<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<style type="text/css">
	#bbs table {
	    width:800px;
	    margin:0 auto;
	    margin-top:20px;
	    border:1px solid black;
	    border-collapse:collapse;
	    font-size:14px;
	    
	}
	
	#bbs table caption {
	    font-size:20px;
	    font-weight:bold;
	    margin-bottom:10px;
	}
	
	#bbs table th {
	    text-align:center;
	    border:1px solid black;
	    padding:4px 10px;
	}
	
	#bbs table td {
	    text-align:left;
	    border:1px solid black;
	    padding:4px 10px;
	}
	
	.no {width:15%}
	.subject {width:30%}
	.writer {width:20%}
	.reg {width:20%}
	.hit {width:15%}
	.title{background:lightsteelblue}
	.odd {background:silver}
	
	input{padding: 5px;}
</style>
<script type="text/javascript">
	function list_go(f) {
		f.action="${pageContext.request.contextPath}/MyController?cmd=list";
		f.submit();
	}
	
	function send_go(f) {
		// 유효성 검사 (alert으로 안채운 칸을 채우라고 경고창을 띄울 수 잇음)
		for (var i = 0; i < f.elements.length; i++) {
			// 요소의 길이만큼 돌림 (이름, 제목, 내용 등이 요소)
			if(f.elements[i].value == null){
				if(i==3||i==2) continue; // 첨부파일은 예외로 만듬
				alert(f.elements[i].name + "을 입력해주세요")
				f.elements[i].focus();
				return;
			}
		}
		f.action="${pageContext.request.contextPath}/MyController?cmd=write_ok";
		f.submit();
	}
</script>
</head>
<body>
	<div id="bbs">
	<form method="post" encType="multipart/form-data">
		<table summary="게시판 글쓰기">
			<caption>게시판 글쓰기</caption>
			<tbody>
				<tr>
					<th>제목:</th>
					<td><input type="text" name="subject" size="45" required></td>
				</tr>
				<tr>
					<th>이름:</th>
					<td><input type="text" name="writer" size="12" required></td>
				</tr>
				<tr>
					<th>내용:</th>
					<td><script src="//cdn.ckeditor.com/4.16.1/standard/ckeditor.js"></script>
					<textarea name="content" cols="50" rows="8"></textarea>
					<script type="text/javascript">
						CKEDITOR.replace('content'); // content에 써지는게 아닌 ckeditor에 써지는 거라 
													 // required가 안채워짐. 해결하는게 숙제
					</script>
					</td>
				</tr>
				<tr>
					<th>첨부파일:</th>
					<td><input type="file" name="file_name"></td>
				</tr>
				<tr>
					<th>비밀번호:</th>
					<td><input type="password" name="pwd" size="12"></td>
				</tr>
				<tr>
					<td colspan="2">
						<input type="button" value="보내기" onclick="send_go(this.form)" />
						<input type="reset" value="다시" />
						<input type="button" value="목록" onclick="list_go(this.form)" />
					</td>
				</tr>
			</tbody>
		</table>
	</form>
	</div>
</body>
</html>

