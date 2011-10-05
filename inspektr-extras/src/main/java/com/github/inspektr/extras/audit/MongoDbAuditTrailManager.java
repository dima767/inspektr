package com.github.inspektr.extras.audit;

import com.github.inspektr.audit.AuditActionContext;
import com.github.inspektr.audit.AuditTrailManager;
import org.springframework.data.mongodb.core.MongoTemplate;

/**
 * Inspektr AuditTrailManager that persists audit trail data in MongoDB
 *
 * This class uses Spring Data MongoDB support
 *
 * @author Dmitriy Kopylenko
 */
public class MongoDbAuditTrailManager implements AuditTrailManager {

    private MongoTemplate mongoTemplate;

    private String mongoCollectionName;

    public MongoDbAuditTrailManager(MongoTemplate mongoTemplate, String mongoCollectionName) {
        this.mongoTemplate = mongoTemplate;
        this.mongoCollectionName = mongoCollectionName;
    }

    public void record(AuditActionContext auditActionContext) {
        this.mongoTemplate.insert(auditActionContext, this.mongoCollectionName);
    }
}



