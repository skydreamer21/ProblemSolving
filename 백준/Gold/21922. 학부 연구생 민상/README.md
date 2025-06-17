# [Gold V] 학부 연구생 민상 - 21922 

[문제 링크](https://www.acmicpc.net/problem/21922) 

### 성능 요약

메모리: 253832 KB, 시간: 2156 ms

### 분류

그래프 이론, 그래프 탐색, 구현, 시뮬레이션

### 제출 일자

2025년 6월 17일 23:03:36

### 문제 설명

<p>학부 연구생으로 새로 연구실에 들어온 민상이는 사용할 자리를 정하려고 한다.</p>

<p>연구실은 격자 모양으로 되어있고 에어컨에서 바람이 상,하,좌,우 $4$방향으로 분다. 물론 에어컨이 위치한 곳에도 바람이 분다.</p>

<p>민상이는 더위를 많이 타서 에어컨 바람이 지나가는 곳 중 하나를 선택하여 앉으려고 한다.</p>

<p>연구실에는 다양한 물건들이 있어 바람의 방향을 바꾼다.</p>

<p>연구실에 있는 물건의 종류는 총 4가지가 있다. 아래 화살표의 의미는 바람이 각 물건에서 바람의 이동을 표시한 것이다.</p>

<table class="table table-bordered">
	<tbody>
		<tr>
			<td style="text-align: center;">물건 종류</td>
			<td style="text-align: center;">물건 모양</td>
			<td style="text-align: center;">바람의 이동 방향</td>
		</tr>
		<tr>
			<td style="text-align: center;">물건 1</td>
			<td style="text-align: center;"><img alt="" src="" style="height: 165px; width: 169px;"></td>
			<td style="text-align: center;"><img alt="" src="" style="height: 140px; width: 200px;"></td>
		</tr>
		<tr>
			<td style="text-align: center;">물건 2</td>
			<td style="text-align: center;"><img alt="" src="" style="height: 158px; width: 169px;"><br>
			 </td>
			<td style="text-align: center;"><img alt="" src="" style="width: 158px; height: 200px;"></td>
		</tr>
		<tr>
			<td style="text-align: center;">물건 3</td>
			<td style="text-align: center;"><img alt="" src="" style="height: 185px; width: 141px;"></td>
			<td style="text-align: center;"><img alt="" src="" style="height: 236px; width: 200px;"></td>
		</tr>
		<tr>
			<td style="text-align: center;">물건 4</td>
			<td style="text-align: center;">
			<p><img alt="" src="" style="height: 185px; width: 122px;"></p>
			</td>
			<td style="text-align: center;"><img alt="" src="" style="width: 250px; height: 210px;"></td>
		</tr>
	</tbody>
</table>

<p>연구실 어디든 민상이가 앉을 수 있는 자리이다. 즉 에어컨이 위치한 자리와 물건이 있는 자리 모두 앉을 수 있다.</p>

<p>민상이가 원하는 자리는 몇 개 있는지 계산해주자.</p>

### 입력 

 <p>첫 번째 줄에는 연구실의 크기가 세로 $N(1 \le N \le 2,000)$, 가로 $M(1 \le M \le 2,000)$ 순으로 주어진다.</p>

<p>두 번째 줄부터 $N + 1$ 줄까지 연구실 내부 구조 정보를 알려주는 값 $M$개가 주어진다.</p>

<p>$1,2,3,4$는 위에서 설명한 물건의 종류이다.</p>

<p>$9$는 에어컨을 의미하고, $0$은 빈 공간을 의미한다.</p>

<p>에어컨은 $0$개 이상 $50$개 이하가 들어온다.</p>

### 출력 

 <p>민상이가 원하는 자리의 개수를 출력한다.</p>

