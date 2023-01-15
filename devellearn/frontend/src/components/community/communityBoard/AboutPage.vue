<template>
  <div>
    <section class="section2" id="AboutUs">
		<span class="title">ABOUT 개미. '개'발에 '미'쳐라!</span>
		<table>
		<tr>
		<td>
		<img src="http://media.fastcampus.co.kr/wp-content/uploads/2021/03/fastcampus-media-coding-img-1-1030x539.png" alt="AboutUs">
		</td>
		<td>
		<span class="title">ABOUT DEVELLEARN</span>
		<span class="sub-title">우리는 때로 무언가를 배워야만 합니다.</span>
		<p>꿈을 이루기 위해서, 하고 싶은걸 해나가기 위해선 그 분야에 대한 배움이 가장 기본적인 요소이기 때문입니다. 하지만, 모두에게 배우기 좋은 환경이 보장 되진 않죠.
너무 비싸서, 거리가 멀어서.. 여러 이유로 당연하다고 생각되어 지는것들이 어떤 누군가에게는 사치가 되기도 합니다. 그런 현실에 도전하려 합니다. DEVLLEARN은 누구나, 경제적으로 시간적 제약없이 내가 원하는 것을 배우고, 지식을 나눌 수 있는 공간입니다.</p>
		<p>당장 좋은 사수가 없거나, 교육을 받지 못하더라도 걱정하지 마세요. DEVLLEARN에서 전문가들에게서 지식과 노하우를 배울 수 있습니다.</p>
		</td>
		</tr>
		</table>
		</section>
		
		<section class="section3" id="OurMission">
		<table>
		<tr>
		<td>
		<span class="title">OUR MISSION</span>
		<span class="sub-title">마케팅보다 좋은 콘텐츠에</span>
		<p>DEVLLEARN은 눈앞의 돈을 쫓기 보다는 옳은 방향을 찾기위해 노력합니다. DEVLLEARN은 다른 곳들과는 달리 수강생수, 강의평가 등을 조작이나 선별 없이 공개합니다. 이는 학습자들이 더 좋은 경험을 스스로 찾아서 하게 만들고, 지식공유자가 더 좋은 학습 콘텐츠를 만들 수 있도록 도와준다고 생각합니다. 학습자, 지식공유자 모두에게 장기적으로 도움이 되는 생태계를 만들기 위해 노력합니다.</p>
		<p> 다양한 꿈을 이룰 수 있는 폭 넓고 깊은 지식을 전달하려 노력하겠습니다. 오픈 플랫폼의 이점을 통해 다양성과 경제성을 모두 높입니다.</p>
		</td>
		<td>
		<img src="https://images.unsplash.com/photo-1499083097717-a156f85f0516?ixid=MXwxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHw%3D&ixlib=rb-1.2.1&auto=format&fit=crop&w=751&q=80" alt="AboutUs">
		</td>
		</tr>
		</table>
		</section>

  </div>
</template>

<script></script>

<style scoped>
@font-face {
    font-family: 'GmarketSansMedium';
    src: url('https://cdn.jsdelivr.net/gh/projectnoonnu/noonfonts_2001@1.1/GmarketSansMedium.woff') format('woff');
    font-weight: normal;
    font-style: normal;
}

*{
  font-family: GmarketSansMedium;
  text-align: left;
}
		html {
        scroll-behavior: smooth;
        }
		
		body{
		margin:0;
		}
		
		.title{
		font-size:2em;
		color:#00336d;
		display:block;
		}
		
		.sub-title{
		font-size:0.8em;
		color:gray;
		display:block;
		}
		
		.btn1{
		padding:10px 20px;
		background:transparent;
		border:1px solid #00336d;
		color:#00336d;
		border-radius:3px;
		outline:none;
		transition:0.5s;
		}
		
		.btn1:hover{
		background:#00336d;
		color:#fff;
		}
		
		.btn2{
		padding:10px 20px;
		border-radius:3px;
		background:#1e1e1e;
		border:1px solid #1e1e1e;
		color:#fff;
		outline:none;
		transition:0.5s;
		}
		
		.btn2:hover{
		background:transparent;
		color:#1e1e1e;
		}
				
		a{
		text-decoration:none;
		}
		
		.section2,.section3{
		width:90%;
		margin:5% 0%;
		margin-left:auto;
		margin-right:auto;
		}
		
		.section2 table,.section3 table{
		width:100%;
		}
		
		.section2 table td,.section3 table td{
		width:50%;
		}
		
		.section2 table td img,.section3 table td img{
		width:80%;
		height:70%;
		margin-left:auto;
		margin-right:auto;
		box-shadow:0px 6px 16px -6px rgba(1,1,1,0.5);
		}
		
		.section3 table td img{
		float:right;
		}
		
		
		@media (max-width:720px){
		.section2 table td, .section3 table td{
		width:100%;
		display:block;
		}
		.section3 table td img{
		float:none;
		margin-top:4%;
		}
		.section2 table td img,.section3 table td img{
		width:100%;
		height:60%;
		}
		}

@media (max-width:300px){
  .section2,.section3{
    font-size:12px;
  }
  .section3 table td img{
		float:none;
		margin-top:8%;
		}
  .btn1,.btn2{
    padding:5px 10px;
  }
}
.footer{
  position:fixed;
  box-shadow:0px 6px 16px -6px rgba(1,1,1,0.5);
  background:#1e1e1e;
  padding:10px 20px;
  color:gray;
  font-size:13px;
  position:fixed;
  bottom:10px;
  right:0;
}
</style>
