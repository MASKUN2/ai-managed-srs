

### 트러블 슈팅
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