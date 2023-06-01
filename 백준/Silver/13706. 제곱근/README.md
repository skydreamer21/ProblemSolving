# [Silver IV] 제곱근 - 13706 

[문제 링크](https://www.acmicpc.net/problem/13706) 

## Study
### 뉴튼 법으로 제곱근 구하기
- 방정식의 근의 근사치를 찾아나가는 과정으로 제곱근을 구한다.
$$ x_(n+1) = x_n - f(x_n)/f'(x_n) $$
- 제곱근을 구하는 방정식은 $ x^2 - N = 0 $ 이라고 할 수 있으므로 $ f(x) = x^2 - N $ 이라고 할 수 있다.
- 따라서 이를 대입해서 정리하면 다음과 같은 식으로 근사치를 구할 수 있다.
$$ x_(n+1) = (x_n + N/x_n)/2 $$

- 이 방법의 시간복잡도를 정확하게 계산은 못하였으나 현재 생각해 본 바로는 약 $log(N) / 2$ 정도의 연산이 필요하지 않을까 추측된다.
(위의 식에서 $x_n$이 제곱근과 같거나 최대한 가까워지면 끝나기 때문에)

- 정수일 경우에는 $ i * i <= N $ 를 만족하는 i의 최댓값을 구하게 된다. 

### Reference
[뉴튼 법을 이용한 근사값 구하기](https://ntalbs.github.io/2014/newtons-method/)
[Best Square Root Method - Algorithm - Function (Precision VS Speed)](https://www.codeproject.com/Articles/69941/Best-Square-Root-Method-Algorithm-Function-Precisi)


### 성능 요약

메모리: 18572 KB, 시간: 164 ms

### 분류

임의 정밀도 / 큰 수 연산, 이분 탐색, 수학

### 문제 설명

<p>정수 N이 주어졌을 때, N의 제곱근을 구하는 프로그램을 작성하시오.</p>

### 입력 

 <p>첫째 줄에 양의 정수 N이 주어진다. 정수 N의 제곱근은 항상 정수이며, N의 길이는 800자리를 넘지 않는다.</p>

### 출력 

 <p>첫째 줄에 정수 N의 제곱근을 출력한다.</p>

