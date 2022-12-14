/*
 * This file is part of dependency-check-core.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 * Copyright (c) 2013 Jeremy Long. All Rights Reserved.
 */
package org.owasp.dependencycheck.analyzer;

import javax.annotation.concurrent.ThreadSafe;
import org.owasp.dependencycheck.Engine;
import org.owasp.dependencycheck.analyzer.exception.AnalysisException;
import org.owasp.dependencycheck.dependency.Dependency;
import org.owasp.dependencycheck.utils.Settings;
import org.owasp.dependencycheck.xml.suppression.SuppressionRule;

/**
 * <p>
 * This is no longer used as a standalone analyzer; rather this is called by the
 * CPE Analyzer directly. TODO - refactor this class so that is not an
 * 'analyzer'.</p>
 *
 * <p>
 * The suppression analyzer processes an externally defined XML document that
 * complies with the suppressions.xsd schema. Any identified CPE entries within
 * the dependencies that match will be removed.</p>
 *
 * @author Jeremy Long
 */
@ThreadSafe
public class CpeSuppressionAnalyzer extends AbstractSuppressionAnalyzer {

    /**
     * The name of the analyzer.
     */
    private static final String ANALYZER_NAME = "Cpe Suppression Analyzer";
    /**
     * The phase that this analyzer is intended to run in.
     */
    private static final AnalysisPhase ANALYSIS_PHASE = AnalysisPhase.POST_IDENTIFIER_ANALYSIS;

    /**
     * Returns the name of the analyzer.
     *
     * @return the name of the analyzer.
     */
    @Override
    public String getName() {
        return ANALYZER_NAME;
    }

    /**
     * Returns the phase that the analyzer is intended to run in.
     *
     * @return the phase that the analyzer is intended to run in.
     */
    @Override
    public AnalysisPhase getAnalysisPhase() {
        return ANALYSIS_PHASE;
    }

    /**
     * <p>
     * Returns the setting key to determine if the analyzer is enabled.</p>
     *
     * @return the key for the analyzer's enabled property
     */
    @Override
    protected String getAnalyzerEnabledSettingKey() {
        return Settings.KEYS.ANALYZER_CPE_SUPPRESSION_ENABLED;
    }

    @Override
    public boolean filter(SuppressionRule rule) {
        return rule.hasCpe();
    }

    @Override
    protected void analyzeDependency(Dependency dependency, Engine engine) throws AnalysisException {
        if (dependency.getVulnerableSoftwareIdentifiersCount() > 0) {
            super.analyzeDependency(dependency, engine);
        }
    }
}
