/*
 * Copyright 2010-2015 JetBrains s.r.o.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.jetbrains.kotlin.idea.folding;

import com.intellij.testFramework.TestDataPath;
import org.jetbrains.kotlin.test.InnerTestClasses;
import org.jetbrains.kotlin.test.JUnit3RunnerWithInners;
import org.jetbrains.kotlin.test.JetTestUtils;
import org.jetbrains.kotlin.test.TestMetadata;
import org.junit.runner.RunWith;

import java.io.File;
import java.util.regex.Pattern;

/** This class is generated by {@link org.jetbrains.kotlin.generators.tests.TestsPackage}. DO NOT MODIFY MANUALLY */
@SuppressWarnings("all")
@InnerTestClasses({
        KotlinFoldingTestGenerated.NoCollapse.class,
        KotlinFoldingTestGenerated.CheckCollapse.class,
})
@RunWith(JUnit3RunnerWithInners.class)
public class KotlinFoldingTestGenerated extends AbstractKotlinFoldingTest {
    @TestMetadata("idea/testData/folding/noCollapse")
    @TestDataPath("$PROJECT_ROOT")
    @RunWith(JUnit3RunnerWithInners.class)
    public static class NoCollapse extends AbstractKotlinFoldingTest {
        public void testAllFilesPresentInNoCollapse() throws Exception {
            JetTestUtils.assertAllTestsPresentByMetadata(this.getClass(), new File("idea/testData/folding/noCollapse"), Pattern.compile("^(.+)\\.kt$"), true);
        }

        @TestMetadata("class.kt")
        public void testClass() throws Exception {
            String fileName = JetTestUtils.navigationMetadata("idea/testData/folding/noCollapse/class.kt");
            doTest(fileName);
        }

        @TestMetadata("function.kt")
        public void testFunction() throws Exception {
            String fileName = JetTestUtils.navigationMetadata("idea/testData/folding/noCollapse/function.kt");
            doTest(fileName);
        }

        @TestMetadata("imports.kt")
        public void testImports() throws Exception {
            String fileName = JetTestUtils.navigationMetadata("idea/testData/folding/noCollapse/imports.kt");
            doTest(fileName);
        }

        @TestMetadata("kdocComments.kt")
        public void testKdocComments() throws Exception {
            String fileName = JetTestUtils.navigationMetadata("idea/testData/folding/noCollapse/kdocComments.kt");
            doTest(fileName);
        }

        @TestMetadata("multilineComments.kt")
        public void testMultilineComments() throws Exception {
            String fileName = JetTestUtils.navigationMetadata("idea/testData/folding/noCollapse/multilineComments.kt");
            doTest(fileName);
        }

        @TestMetadata("object.kt")
        public void testObject() throws Exception {
            String fileName = JetTestUtils.navigationMetadata("idea/testData/folding/noCollapse/object.kt");
            doTest(fileName);
        }

        @TestMetadata("oneImport.kt")
        public void testOneImport() throws Exception {
            String fileName = JetTestUtils.navigationMetadata("idea/testData/folding/noCollapse/oneImport.kt");
            doTest(fileName);
        }
    }

    @TestMetadata("idea/testData/folding/checkCollapse")
    @TestDataPath("$PROJECT_ROOT")
    @RunWith(JUnit3RunnerWithInners.class)
    public static class CheckCollapse extends AbstractKotlinFoldingTest {
        public void testAllFilesPresentInCheckCollapse() throws Exception {
            JetTestUtils.assertAllTestsPresentByMetadata(this.getClass(), new File("idea/testData/folding/checkCollapse"), Pattern.compile("^(.+)\\.kt$"), true);
        }

        @TestMetadata("headerKDoc.kt")
        public void testHeaderKDoc() throws Exception {
            String fileName = JetTestUtils.navigationMetadata("idea/testData/folding/checkCollapse/headerKDoc.kt");
            doSettingsFoldingTest(fileName);
        }

        @TestMetadata("headerMultilineComment.kt")
        public void testHeaderMultilineComment() throws Exception {
            String fileName = JetTestUtils.navigationMetadata("idea/testData/folding/checkCollapse/headerMultilineComment.kt");
            doSettingsFoldingTest(fileName);
        }

        @TestMetadata("imports.kt")
        public void testImports() throws Exception {
            String fileName = JetTestUtils.navigationMetadata("idea/testData/folding/checkCollapse/imports.kt");
            doSettingsFoldingTest(fileName);
        }
    }
}