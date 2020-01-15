<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<style>
	#map{
		width: 400px;
		height: 100px;
		
	}
</style>
<div>
글쓰기 하기 위한 폼 입니다.
<h1> <%=request.getRequestURI().substring(request.getContextPath().length())%></h1>
<form id = "registerForm" action="register" method="post" enctype="multipart/form-data">
	<select name="pcate">
		<option value="병원">병원</option>
		<option value="음식">음식</option>
		<option value="제품">제품</option>
	</select>
	제목 : <input type="text" name="pname"> <br>
	
 <div id="floating-panel">
      <input id="address" type="text" value="Sydney, NSW">
      <input id="adr" type="button" value="Geocode">
    </div>
    <div id="map"></div>
    <script>
      function initMap() {
        var map = new google.maps.Map(document.getElementById('map'), {
          zoom: 15,
          center: {lat: -34.397, lng: 150.644}
        });
        var geocoder = new google.maps.Geocoder();

        document.getElementById('adr').addEventListener('click', function() {
          geocodeAddress(geocoder, map);
        });
      }

      function geocodeAddress(geocoder, resultsMap) {
        var address = document.getElementById('address').value;
        geocoder.geocode({'address': address}, function(results, status) {
          if (status === 'OK') {
            resultsMap.setCenter(results[0].geometry.location);
            var marker = new google.maps.Marker({
              map: resultsMap,
              position: results[0].geometry.location
            });
          } else {
            alert('Geocode was not successful for the following reason: ' + status);
          }
        });
      }
    </script>
    <script async defer
    src="https://maps.googleapis.com/maps/api/js?key=AIzaSyBggZ8qinjU9aNYY_vCqfzv_C7PBA5v680&callback=initMap">
    </script>
	<br>
	내용 : <textarea rows="10" cols="20" name="pinfo"></textarea> <br>
	연락처 : <input type="text" name="ptel"> <br>
	 파일 : <input type="file" name="filename">
	 
	 
	 
	<input type="submit" value="글쓰기">
	
	
	
	<div class="fileDrop">파일을 드래그앤 드랍</div>
	
	
	<div class="uploadedList"></div>
	
</form>
</div>
<script type="text/javascript" src="resources/js/uploadwrite.js?ver=5"></script>
