package net.sourceforge.jabm.spring;

import cern.jet.random.AbstractContinousDistribution;
import org.springframework.beans.factory.annotation.Required;

public abstract class AbstractRandomVariateFactoryBean {

    /**
     * The distribution used to generate the random variate.
     */
    protected AbstractContinousDistribution distribution;

    public AbstractRandomVariateFactoryBean() {
        super();
    }

    public boolean isSingleton() {
        return false;
    }

    public AbstractContinousDistribution getDistribution() {
        return distribution;
    }

    /**
     * The probability distribution used to generate the value of this bean.
     */
    @Required
    public void setDistribution(AbstractContinousDistribution distribution) {
        this.distribution = distribution;
    }

}
