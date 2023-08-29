<h1>Home And Room Project</h1>
<h5>부동산 웹 프로젝트</h5>
<img src="https://github.com/minseung1226/project/assets/102594142/03c52576-1eb8-48ce-88ea-13ec3ee3c842">

<span><b>Home And Room</b> 프로젝트는 기존의 부동산 중개 사이트와는 다른 접근 방식을 가진 부동산 웹 어플리케이션입니다.
우리의 서비스는 부동산과 임차인 간의 중개 없이, 직접적인 연결을 지원합니다. 임대인이 매물을 등록하고 임차인이 문의를 하여 
매칭되는 형식으로 서비스가 진행됩니다. 더 나아가, 우리는 <b>계약서 폼을 제공하여 사용자들이 편리하게 필요한 계약서를 작성하고 생성할 수 있도록 지원합니다.</b></span>
<br>
<br>
<br>
<h2>:sunny: 기술스택</h2>
<ul>
  <li><b>프론트앤드 </b> <span>: HTML ,CSS ,JavaScript ,Thymeleaf </span></li>
  <li><b>백앤드 </b><span>Java17 ,Spring Boot</span></li>
  <li><b>데이터베이스 </b><span>: H2 ,MySQL ,SpringDataJPA ,QueryDsL</span></li>
  <LI><b>API </b> <span>: 카카오 맵 API, 카카오 로그인 API</span></LI>
  <li><b>PDF 생성</b><SPAN> : iText pdf 라이브러리</SPAN></li>
  <li><b>SMS 전송</b> <SPAN> : COOL SMS 라이브러리</SPAN></li>
</ul>
<br><br><br>

<H2>:date:프로젝트 기간</H2>
<ul>
  <li>2023.04.28~2023.08.19</li>
</ul>
<br><br><br>

<h2>:thought_balloon: 도메인 엔티티 설계</h2>
<img src="https://github.com/minseung1226/project/assets/102594142/fe9ecb8a-3210-4e4f-aad9-f6b30f95bd5f">
<Br><br><br>

<h2>:speech_balloon: ERD</h2>
<IMG SRC="https://github.com/minseung1226/project/assets/102594142/06e408ee-4c1f-4205-bca7-53e31f520a34">
<br><br><br>
<br>


<h2>프로젝트 결과</h2>

<h3>🏡홈페이지</h3>  
<img src="https://github.com/minseung1226/project/assets/102594142/eda1a4f3-dd43-4eb3-b73d-d850e396e70b">
- **카카오 맵 API**
    - 홈페이지는 카카오 맵 API를 활용하여 부동산 매물을 지도상에 표시합니다. 이를 통해 이용자는 지도에서 매물들의 위치를 시각적으로 확인할 수 있습니다.
    - 클러스터러를 사용하여 지도 상에 여러 매물이 밀집되어 있는 경우 사용자에게 직관적으로 보여줍니다.
- **Ajax를 통한 실시간 업데이트**
    - 지도를 드래그하거나 확대/축소할 때마다 Ajax를 사용하여 해당 영역에 위치한 매물들을 동적으로 불러와 지도에 업데이트 합니다.
    - 이 기능을 통해 사용자든 지도를 움직일 때마다 실제 매물 정보를 즉시 확인할 수 있습니다.
- **필터와 검색**
    - Ajax를 통해 업데이트를 할 때 위도 경도 뿐만 아니라 검색어,상세 필터에 대한 정보까지 서버에 요청해서 사용자가 원하는 매물들을 검색 할 수 있습니다.-
