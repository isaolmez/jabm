package net.sourceforge.jabm.report;

import java.util.List;
import net.sourceforge.jabm.strategy.Strategy;
import org.apache.commons.math3.stat.descriptive.AggregateSummaryStatistics;
import org.apache.commons.math3.stat.descriptive.StatisticalSummary;

/**
 * (C) Steve Phelps 2015
 */
public class AggregatePayoffMap extends PayoffMap {

    public AggregatePayoffMap(List<Strategy> strategies) {
        super(strategies);
    }

    @Override
    public StatisticalSummary createStatisticalSummary(Strategy s) {
        return new AggregateSummaryStatistics();
    }
}
