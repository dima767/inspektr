package com.github.inspektr.extras.audit;

import com.github.inspektr.audit.AuditActionContext;
import com.github.inspektr.audit.AuditTrailManager;
import org.opencredo.esper.EsperTemplate;

/**
 * Inspektr AuditTrailManager that sends audit trail data as Esper events
 * <p/>
 * This class uses EsperTemplate by Opencredo @see <a href="https://github.com/opencredo/opencredo-esper">opencredo-esper</a>
 *
 * @author Dmitriy Kopylenko
 */
public class EsperAuditTrailManager implements AuditTrailManager {

    private EsperTemplate esperTemplate;

    public EsperAuditTrailManager(EsperTemplate esperTemplate) {
        this.esperTemplate = esperTemplate;
    }

    public void record(AuditActionContext auditActionContext) {
        this.esperTemplate.sendEvent(auditActionContext);
    }
}
