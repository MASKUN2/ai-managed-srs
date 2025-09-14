
# 요구요구 (AI가 관리하는 요구사항명세서)
AI가 관리하는 요구사항명세서

#### 예시1
```text

사용자: 유저 요구사항을 추가해줘. 사용자는 10자의 닉네임을 가지고 이메일을 가지고 있어야해
AI: 알겠습니다. 유저(User) 도메인에 대한 요구사항 명세서를 작성하겠습니다. 기존에 검색된 문서가 없으므로 신규 문서를 생성하겠습니다. 


```
### 요구사항

- 사용자는 웹 기반의 대화창에서 AI와 대화가 가능하다.
- 클라이언트는 타임리프로 개발한다.
- 채팅경로는 /chat 으로 한다.
- 응답은 실시간으로 전송되며 응답 토큰의 최대크기를 2000 으로 한다.
- 지난 대화 기록을 기억한다.
- 채팅창에 프리셋된 프롬프트를 제시한다. \[
  '안녕? 자기소개해줘',
  '소프트웨어 개발의 "요구사항"이란 무엇인지 알려줘'
  ]

### 공부한 방법

#### Spring AI 레퍼런스 문서 원어로 읽기

https://docs.spring.io/spring-ai/reference/api/chatclient.html

#### 구현중 발생한 이슈를 해결하기 위해 GitHub Spring AI Repository 이슈에서 검색해서 읽기

https://github.com/spring-projects

#### 참고서적 읽기

- 최고의 프롬프트 엔지니어링 강의 - 김진중

### 용어정리

| 한글 : 영어          | 설명                               |
|------------------|----------------------------------|
| 채팅:Chat          | 사용자가 메세지를 주고 봇으로부터 응답을 받는 행위를 의미 |
| 대화:Conversation  | 도메인이며 여러 메세지를 가짐                 |
| 메세지:Message      | 여러 컨텐츠를 담을 수 있음을 암시              |
| 내부의 정보: Content  | 주로 메세지 안의 문자열 정보를 의미             |
| 사용자:User         |                                  |
| 봇:Assistant      |                                  |
| 연속됨, 실시간:Stream  | 챗봇의 응답은 주로 StreamResponse        |
| 응답을 받다: Response |                                  |
| 찾다:Find          | 없을 수도 있음                         |
| 가져오다:Require     | 없어서는 안됨                          |

### 이슈노트

#### f9659aedf8cde55de5890580fc385a3d135f5ded
##### 문제
```text
org.springframework.ai.vectorstore.pgvector.autoconfigure.PgVectorStoreAutoConfiguration required a single bean, but 2 were found:

- cohereEmbeddingModel: defined by method 'cohereEmbeddingModel' in class path resource [org/springframework/ai/model/bedrock/cohere/autoconfigure/BedrockCohereEmbeddingAutoConfiguration.class]

- titanEmbeddingModel: defined by method 'titanEmbeddingModel' in class path resource [org/springframework/ai/model/bedrock/titan/autoconfigure/BedrockTitanEmbeddingAutoConfiguration.class]
```
##### 원인 
`org.springframework.ai:spring-ai-starter-vector-store-pgvector`가 `VectorStore`를 자동 구성 할 때 의존하는 임베딩 모델이 자동 구성시 두개가 동시에 등록됨

##### 해결
`BedrockCohereEmbeddingAutoConfiguration.class , BedrockTitanEmbeddingAutoConfiguration.class` 컨디셔널 빈 등록 조건을 보고 해당 프로퍼티를 추가하여 해결 
```yaml
spring.ai.model.embedding=bedrock-titan
```

#### 36bb7cbd87d3902df800605610f7476ee0998999

##### 문제

`spring.ai.bedrock.converse.chat.options.max-tokens` 의 프로퍼티를 resolve 하지못함

##### 원인

프레임워크의 관련 클래스가 인터페이스이고 구현체가 없어서 생긴 문제. 다만 런타임에 인식은 하는 상황

##### 조치 보류

깃허브에 관련 이슈가 등록되고 수정된 부분이 다음 버전에 머지된 것을 확인하였음.

#### 738d1e8bda08d9e8e2b6b5d84e551647f5bc5faa, b5e0e7e71f6bd90e4aa14b1e751cf12cadaea80d

##### 문제

봇의 응답을 스트리밍 하는 과정에서 SSE 방식으로 서버 측 컨텐츠 타입을 `text/event-stream`로 하여 구현을 시도했지만 클라이언트(Thymeleaf)에서 수신 데이터 형식
`data: {text} \\n\\n` 파싱의 어려움을 겪음 몇몇 응답에서는 `data: \\n data: \\n` 형식으로 오는 문제 발생. JS의 `fetch()`로는 이것을 구현하기가 복잡하였음

##### 고민

요청후 이벤트 수신을 받는 클라이언트 소스에서 JS의 `fetch()` , `eventSource()`를 조합하는 방식을 고려했으나 성격이 맞지 못했고. Microsoft의 Fetch-event-source
라이브러리를 도입할 것을 고려했으나 그것이 ESM이고 현재의 클라이언트(Thymeleaf) 환경에서는 자동빌드나 자동완성을 지원하지 않아서 보류함.

##### 해결

응답 컨텐츠 타입을 변경 `Content-Type: text/plain`. 서버 컨트롤러 메서드 리턴타입을 수정`Flux<String>`. 청크`Transfer-Encoding: chunked` 응답을 하도록
변경함. 클라이언트 소스에서는 `fetch()` 만 사용하여 응답 바디를 실시간으로 읽어 렌더링하는 방식으로 구현함

#### 1364dbf5a28f5470fa6abb3c499845e5601052f7

##### 문제

```text
software.amazon.awssdk.services.bedrockruntime.model.ValidationException: A conversation must start with a user message. Try again with a conversation that starts with a user message. 
```

AWS bedrock LLM Model Nova Mirco API 사용시 요청문의 messages 리스트의 0번 객체가 UserMessage이지 않아서 요청이 거부된 문제가 발생함

##### 해결

`ChatMemory.maxMessages({size})`가 홀수인 경우 요청문의 첫번째 message 객체가 AssistantMessage가 들어가게 됨. 이를 짝수로 변경하여 회피하였음.

####  

##### 문제

`JdbcChatMemoryRepository`,`ChatMemory`의 기본 구현이 `ChatMemory.maxMessages({size})`크기를 제외한 나머지 ChatMemory를 초기화하므로 따로 대화 기록이
저장되지 않음