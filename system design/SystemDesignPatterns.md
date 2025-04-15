# System Design Patterns and Concepts

This document provides a comprehensive reference for system design concepts and patterns commonly asked in technical interviews, especially for senior engineering and architectural positions.

## Core Concepts

### Scalability

#### Horizontal vs. Vertical Scaling
- **Horizontal Scaling (Scale Out)**: Adding more machines to the pool of resources
- **Vertical Scaling (Scale Up)**: Adding more power (CPU, RAM) to existing machines
- **Trade-offs**: Horizontal scaling provides better resilience and flexibility but introduces distributed system complexity

#### Load Balancing
- **Purpose**: Distribute traffic across multiple servers
- **Algorithms**: Round Robin, Least Connections, IP Hash, Weighted Round Robin, etc.
- **Layer 4 vs Layer 7**: Transport layer vs. Application layer load balancing
- **Implementation**: ELB, NGINX, HAProxy, F5

### Database Design

#### ACID Properties
- **Atomicity**: Transactions are all-or-nothing
- **Consistency**: Database state remains valid after every transaction
- **Isolation**: Concurrent transactions don't interfere with each other
- **Durability**: Committed transactions persist even after system failures

#### CAP Theorem
- **Consistency**: All clients see the same data at the same time
- **Availability**: Every request receives a response (success/failure)
- **Partition Tolerance**: System continues operating despite network partitions
- **Trade-offs**: It's impossible to guarantee all three simultaneously

#### SQL vs. NoSQL
- **SQL**: Structured, ACID-compliant, strong schema, vertical scaling
- **NoSQL Types**:
  - Document (MongoDB, CouchDB)
  - Key-Value (Redis, DynamoDB)
  - Column-Family (Cassandra, HBase)
  - Graph (Neo4j, Neptune)
- **When to use which**: Data structure, query patterns, consistency requirements

#### Database Sharding
- **Horizontal Partitioning**: Splitting database across multiple servers
- **Sharding Keys**: Determining which shard stores which data
- **Sharding Strategies**: Range-based, hash-based, directory-based
- **Challenges**: Joins, rebalancing, hotspots

### Caching

#### Caching Strategies
- **Cache-Aside**: Application manages cache population
- **Read-Through**: Cache manages data retrieval from database
- **Write-Through**: Data written to cache and database simultaneously
- **Write-Behind**: Data written to cache, then asynchronously to database
- **Refresh-Ahead**: Proactively update cache before expiration

#### Cache Eviction Policies
- **LRU** (Least Recently Used)
- **LFU** (Least Frequently Used)
- **FIFO** (First In First Out)
- **TTL** (Time To Live)

#### Cache Coherence
- **Challenges**: Stale data, thundering herd problem
- **Solutions**: Cache invalidation, versioning, soft TTL

### Microservices Architecture

#### Core Principles
- **Single Responsibility**: Each service does one thing well
- **Domain-Driven Design**: Services aligned with business domains
- **Decentralized Governance**: Teams choose their own tech stacks
- **Smart Endpoints, Dumb Pipes**: Business logic in services, simple communication

#### Service Communication
- **Synchronous**: REST, gRPC, GraphQL
- **Asynchronous**: Message Queues (RabbitMQ, Kafka), Event Streaming
- **Service Mesh**: Istio, Linkerd, Consul

#### Common Patterns
- **API Gateway**: Single entry point for clients
- **Circuit Breaker**: Prevent cascading failures
- **Bulkhead**: Isolate failures to contain damage
- **CQRS**: Separate read and write operations
- **Event Sourcing**: Store state changes as event logs

### Distributed Systems

#### Consistency Models
- **Strong Consistency**: All readers see the latest write
- **Eventual Consistency**: All readers eventually see the latest write
- **Causal Consistency**: Related operations are seen in order

#### Consensus Algorithms
- **Paxos**: Classic algorithm for distributed consensus
- **Raft**: More understandable alternative to Paxos
- **ZAB**: Powers Zookeeper

#### Distributed Transactions
- **Two-Phase Commit**: Prepare and commit phases
- **Saga Pattern**: Chain of local transactions with compensating actions
- **Distributed Locks**: Mutual exclusion across multiple services

### Data Processing

#### Batch Processing
- **Use Cases**: Reports, ETL, bulk operations
- **Technologies**: Hadoop, Spark, MapReduce
- **Considerations**: Processing time, latency, fault tolerance

#### Stream Processing
- **Use Cases**: Real-time analytics, monitoring, fraud detection
- **Technologies**: Kafka Streams, Flink, Storm, Spark Streaming
- **Processing Semantics**: At-least-once, at-most-once, exactly-once

## System Design Examples

### URL Shortener

#### Requirements
- **Functional**: Shorten URLs, redirect to original URL
- **Non-Functional**: High availability, low latency, scalability

#### Key Components
1. **Application Service**: Handles shortening and redirection
2. **Database**: Stores URL mappings
3. **Cache**: Speeds up frequent redirections

#### Design Decisions
- **ID Generation**: Random vs. Base62 encoding of incremental IDs
- **Storage**: NoSQL preferred for simple key-value lookups
- **Caching**: Heavily cache popular redirects (Read-heavy workload)

### Chat System

#### Requirements
- **Functional**: 1-on-1 chat, group chat, online presence
- **Non-Functional**: Low latency, high availability, message delivery guarantees

#### Key Components
1. **Chat Service**: Manages message handling
2. **Presence Service**: Tracks user online status
3. **Notification Service**: Pushes updates to clients
4. **Message Store**: Persists chat history

#### Design Decisions
- **Connection Protocol**: WebSockets for real-time communication
- **Message Delivery**: Queue-based approach for reliable delivery
- **Storage**: Combination of relational DB for user data and NoSQL for messages

### Netflix/Video Streaming

#### Requirements
- **Functional**: Content discovery, video playback, user profiles
- **Non-Functional**: High availability, global scale, adaptive streaming

#### Key Components
1. **Content Delivery Network (CDN)**: Edge caching of popular content
2. **Recommendation Engine**: Personalized content suggestions
3. **Video Transcoding Service**: Different formats and bitrates
4. **User Profile Service**: Preferences and viewing history

#### Design Decisions
- **Global Distribution**: Regional caches to reduce latency
- **Adaptive Bitrate Streaming**: Adjust quality based on network conditions
- **Microservices Architecture**: Decoupled services for flexibility

### Ride-Sharing Service

#### Requirements
- **Functional**: Matching riders with drivers, tracking rides, payments
- **Non-Functional**: Low latency matching, geospatial queries, reliability

#### Key Components
1. **Location Service**: Tracks driver/rider positions
2. **Matching Service**: Pairs riders with drivers
3. **Payment Service**: Handles transactions
4. **Routing Service**: Calculates optimal routes

#### Design Decisions
- **Geospatial Indexing**: QuadTree or S2 cells for efficient driver lookup
- **Consistent Hashing**: For distributing location tracking
- **Real-time Updates**: WebSockets/long polling for position updates

## Interview Approach

### Step-by-Step Method

1. **Clarify Requirements**
   - Functional and non-functional requirements
   - Scale (users, data, requests)
   - Ask questions about priority features

2. **Define API**
   - Key endpoints
   - Request/response formats
   - Error handling

3. **High-Level Design**
   - Major components
   - Data flow between components
   - Back-of-envelope calculations

4. **Detailed Design**
   - Database schema
   - Technology choices
   - Algorithms for key operations

5. **Address Bottlenecks**
   - Identify potential issues
   - Propose scaling solutions
   - Implement fault tolerance

6. **Trade-offs**
   - Explicitly state pros and cons of your design choices
   - Consider alternative approaches
   - Discuss monitoring and observability

### Common Pitfalls to Avoid

- Diving into implementation details too early
- Not clarifying requirements before designing
- Neglecting non-functional requirements
- Overcomplicating solutions
- Not explicitly stating trade-offs
- Focusing too much on one aspect (e.g., just the database)

## Additional Learning Resources

- [System Design Primer](https://github.com/donnemartin/system-design-primer)
- [Designing Data-Intensive Applications](https://dataintensive.net/) by Martin Kleppmann
- [High Scalability Blog](http://highscalability.com/)
- [AWS Architecture Center](https://aws.amazon.com/architecture/)
- [Google SRE Book](https://landing.google.com/sre/books/)
