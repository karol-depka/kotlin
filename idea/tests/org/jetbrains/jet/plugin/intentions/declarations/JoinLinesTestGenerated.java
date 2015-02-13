/*
 * Copyright 2010-2014 JetBrains s.r.o.
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

package org.jetbrains.jet.plugin.intentions.declarations;

import com.intellij.testFramework.TestDataPath;
import org.jetbrains.jet.JUnit3RunnerWithInners;
import org.jetbrains.jet.JetTestUtils;
import org.jetbrains.jet.test.InnerTestClasses;
import org.jetbrains.jet.test.TestMetadata;
import org.junit.runner.RunWith;

import java.io.File;
import java.util.regex.Pattern;

/** This class is generated by {@link org.jetbrains.jet.generators.tests.TestsPackage}. DO NOT MODIFY MANUALLY */
@SuppressWarnings("all")
@TestMetadata("idea/testData/joinLines")
@TestDataPath("$PROJECT_ROOT")
@InnerTestClasses({JoinLinesTestGenerated.DeclarationAndAssignment.class, JoinLinesTestGenerated.NestedIfs.class, JoinLinesTestGenerated.RemoveBraces.class})
@RunWith(JUnit3RunnerWithInners.class)
public class JoinLinesTestGenerated extends AbstractJoinLinesTest {
    public void testAllFilesPresentInJoinLines() throws Exception {
        JetTestUtils.assertAllTestsPresentByMetadata(this.getClass(), new File("idea/testData/joinLines"), Pattern.compile("^(.+)\\.kt$"), true);
    }

    @TestMetadata("idea/testData/joinLines/declarationAndAssignment")
    @TestDataPath("$PROJECT_ROOT")
    @RunWith(JUnit3RunnerWithInners.class)
    public static class DeclarationAndAssignment extends AbstractJoinLinesTest {
        public void testAllFilesPresentInDeclarationAndAssignment() throws Exception {
            JetTestUtils.assertAllTestsPresentByMetadata(this.getClass(), new File("idea/testData/joinLines/declarationAndAssignment"), Pattern.compile("^(.+)\\.kt$"), true);
        }

        @TestMetadata("blankLineBetween.kt")
        public void testBlankLineBetween() throws Exception {
            String fileName = JetTestUtils.navigationMetadata("idea/testData/joinLines/declarationAndAssignment/blankLineBetween.kt");
            doTest(fileName);
        }

        @TestMetadata("commentBetween.kt")
        public void testCommentBetween() throws Exception {
            String fileName = JetTestUtils.navigationMetadata("idea/testData/joinLines/declarationAndAssignment/commentBetween.kt");
            doTest(fileName);
        }

        @TestMetadata("longInit.kt")
        public void testLongInit() throws Exception {
            String fileName = JetTestUtils.navigationMetadata("idea/testData/joinLines/declarationAndAssignment/longInit.kt");
            doTest(fileName);
        }

        @TestMetadata("longInit2.kt")
        public void testLongInit2() throws Exception {
            String fileName = JetTestUtils.navigationMetadata("idea/testData/joinLines/declarationAndAssignment/longInit2.kt");
            doTest(fileName);
        }

        @TestMetadata("propertyWithAnnotation.kt")
        public void testPropertyWithAnnotation() throws Exception {
            String fileName = JetTestUtils.navigationMetadata("idea/testData/joinLines/declarationAndAssignment/propertyWithAnnotation.kt");
            doTest(fileName);
        }

        @TestMetadata("simpleInit.kt")
        public void testSimpleInit() throws Exception {
            String fileName = JetTestUtils.navigationMetadata("idea/testData/joinLines/declarationAndAssignment/simpleInit.kt");
            doTest(fileName);
        }

        @TestMetadata("simpleInit2.kt")
        public void testSimpleInit2() throws Exception {
            String fileName = JetTestUtils.navigationMetadata("idea/testData/joinLines/declarationAndAssignment/simpleInit2.kt");
            doTest(fileName);
        }

        @TestMetadata("simpleInitWithBackticks.kt")
        public void testSimpleInitWithBackticks() throws Exception {
            String fileName = JetTestUtils.navigationMetadata("idea/testData/joinLines/declarationAndAssignment/simpleInitWithBackticks.kt");
            doTest(fileName);
        }

        @TestMetadata("simpleInitWithBackticks2.kt")
        public void testSimpleInitWithBackticks2() throws Exception {
            String fileName = JetTestUtils.navigationMetadata("idea/testData/joinLines/declarationAndAssignment/simpleInitWithBackticks2.kt");
            doTest(fileName);
        }

        @TestMetadata("simpleInitWithBackticks3.kt")
        public void testSimpleInitWithBackticks3() throws Exception {
            String fileName = JetTestUtils.navigationMetadata("idea/testData/joinLines/declarationAndAssignment/simpleInitWithBackticks3.kt");
            doTest(fileName);
        }

        @TestMetadata("simpleInitWithComments.kt")
        public void testSimpleInitWithComments() throws Exception {
            String fileName = JetTestUtils.navigationMetadata("idea/testData/joinLines/declarationAndAssignment/simpleInitWithComments.kt");
            doTest(fileName);
        }

        @TestMetadata("simpleInitWithComments2.kt")
        public void testSimpleInitWithComments2() throws Exception {
            String fileName = JetTestUtils.navigationMetadata("idea/testData/joinLines/declarationAndAssignment/simpleInitWithComments2.kt");
            doTest(fileName);
        }

        @TestMetadata("simpleInitWithSemicolons.kt")
        public void testSimpleInitWithSemicolons() throws Exception {
            String fileName = JetTestUtils.navigationMetadata("idea/testData/joinLines/declarationAndAssignment/simpleInitWithSemicolons.kt");
            doTest(fileName);
        }

        @TestMetadata("simpleInitWithSemicolons2.kt")
        public void testSimpleInitWithSemicolons2() throws Exception {
            String fileName = JetTestUtils.navigationMetadata("idea/testData/joinLines/declarationAndAssignment/simpleInitWithSemicolons2.kt");
            doTest(fileName);
        }

        @TestMetadata("simpleInitWithSemicolons3.kt")
        public void testSimpleInitWithSemicolons3() throws Exception {
            String fileName = JetTestUtils.navigationMetadata("idea/testData/joinLines/declarationAndAssignment/simpleInitWithSemicolons3.kt");
            doTest(fileName);
        }

        @TestMetadata("simpleInitWithType.kt")
        public void testSimpleInitWithType() throws Exception {
            String fileName = JetTestUtils.navigationMetadata("idea/testData/joinLines/declarationAndAssignment/simpleInitWithType.kt");
            doTest(fileName);
        }

        @TestMetadata("simpleInitWithType2.kt")
        public void testSimpleInitWithType2() throws Exception {
            String fileName = JetTestUtils.navigationMetadata("idea/testData/joinLines/declarationAndAssignment/simpleInitWithType2.kt");
            doTest(fileName);
        }
    }

    @TestMetadata("idea/testData/joinLines/nestedIfs")
    @TestDataPath("$PROJECT_ROOT")
    @RunWith(JUnit3RunnerWithInners.class)
    public static class NestedIfs extends AbstractJoinLinesTest {
        public void testAllFilesPresentInNestedIfs() throws Exception {
            JetTestUtils.assertAllTestsPresentByMetadata(this.getClass(), new File("idea/testData/joinLines/nestedIfs"), Pattern.compile("^(.+)\\.kt$"), true);
        }

        @TestMetadata("BlockBody.kt")
        public void testBlockBody() throws Exception {
            String fileName = JetTestUtils.navigationMetadata("idea/testData/joinLines/nestedIfs/BlockBody.kt");
            doTest(fileName);
        }

        @TestMetadata("ComplexCondition1.kt")
        public void testComplexCondition1() throws Exception {
            String fileName = JetTestUtils.navigationMetadata("idea/testData/joinLines/nestedIfs/ComplexCondition1.kt");
            doTest(fileName);
        }

        @TestMetadata("ComplexCondition2.kt")
        public void testComplexCondition2() throws Exception {
            String fileName = JetTestUtils.navigationMetadata("idea/testData/joinLines/nestedIfs/ComplexCondition2.kt");
            doTest(fileName);
        }

        @TestMetadata("InnerWithElse.kt")
        public void testInnerWithElse() throws Exception {
            String fileName = JetTestUtils.navigationMetadata("idea/testData/joinLines/nestedIfs/InnerWithElse.kt");
            doTest(fileName);
        }

        @TestMetadata("OuterWithElse.kt")
        public void testOuterWithElse() throws Exception {
            String fileName = JetTestUtils.navigationMetadata("idea/testData/joinLines/nestedIfs/OuterWithElse.kt");
            doTest(fileName);
        }

        @TestMetadata("Simple.kt")
        public void testSimple() throws Exception {
            String fileName = JetTestUtils.navigationMetadata("idea/testData/joinLines/nestedIfs/Simple.kt");
            doTest(fileName);
        }
    }

    @TestMetadata("idea/testData/joinLines/removeBraces")
    @TestDataPath("$PROJECT_ROOT")
    @RunWith(JUnit3RunnerWithInners.class)
    public static class RemoveBraces extends AbstractJoinLinesTest {
        public void testAllFilesPresentInRemoveBraces() throws Exception {
            JetTestUtils.assertAllTestsPresentByMetadata(this.getClass(), new File("idea/testData/joinLines/removeBraces"), Pattern.compile("^(.+)\\.kt$"), true);
        }

        @TestMetadata("CommentAfterStatement.kt")
        public void testCommentAfterStatement() throws Exception {
            String fileName = JetTestUtils.navigationMetadata("idea/testData/joinLines/removeBraces/CommentAfterStatement.kt");
            doTest(fileName);
        }

        @TestMetadata("CommentAfterStatement2.kt")
        public void testCommentAfterStatement2() throws Exception {
            String fileName = JetTestUtils.navigationMetadata("idea/testData/joinLines/removeBraces/CommentAfterStatement2.kt");
            doTest(fileName);
        }

        @TestMetadata("CommentBeforeStatement.kt")
        public void testCommentBeforeStatement() throws Exception {
            String fileName = JetTestUtils.navigationMetadata("idea/testData/joinLines/removeBraces/CommentBeforeStatement.kt");
            doTest(fileName);
        }

        @TestMetadata("CommentBeforeStatement2.kt")
        public void testCommentBeforeStatement2() throws Exception {
            String fileName = JetTestUtils.navigationMetadata("idea/testData/joinLines/removeBraces/CommentBeforeStatement2.kt");
            doTest(fileName);
        }

        @TestMetadata("DoWhile.kt")
        public void testDoWhile() throws Exception {
            String fileName = JetTestUtils.navigationMetadata("idea/testData/joinLines/removeBraces/DoWhile.kt");
            doTest(fileName);
        }

        @TestMetadata("Else.kt")
        public void testElse() throws Exception {
            String fileName = JetTestUtils.navigationMetadata("idea/testData/joinLines/removeBraces/Else.kt");
            doTest(fileName);
        }

        @TestMetadata("For.kt")
        public void testFor() throws Exception {
            String fileName = JetTestUtils.navigationMetadata("idea/testData/joinLines/removeBraces/For.kt");
            doTest(fileName);
        }

        @TestMetadata("FunctionBody.kt")
        public void testFunctionBody() throws Exception {
            String fileName = JetTestUtils.navigationMetadata("idea/testData/joinLines/removeBraces/FunctionBody.kt");
            doTest(fileName);
        }

        @TestMetadata("If.kt")
        public void testIf() throws Exception {
            String fileName = JetTestUtils.navigationMetadata("idea/testData/joinLines/removeBraces/If.kt");
            doTest(fileName);
        }

        @TestMetadata("IfWithElse.kt")
        public void testIfWithElse() throws Exception {
            String fileName = JetTestUtils.navigationMetadata("idea/testData/joinLines/removeBraces/IfWithElse.kt");
            doTest(fileName);
        }

        @TestMetadata("LambdaBody.kt")
        public void testLambdaBody() throws Exception {
            String fileName = JetTestUtils.navigationMetadata("idea/testData/joinLines/removeBraces/LambdaBody.kt");
            doTest(fileName);
        }

        @TestMetadata("NotSingleLineStatement.kt")
        public void testNotSingleLineStatement() throws Exception {
            String fileName = JetTestUtils.navigationMetadata("idea/testData/joinLines/removeBraces/NotSingleLineStatement.kt");
            doTest(fileName);
        }

        @TestMetadata("TwoStatements.kt")
        public void testTwoStatements() throws Exception {
            String fileName = JetTestUtils.navigationMetadata("idea/testData/joinLines/removeBraces/TwoStatements.kt");
            doTest(fileName);
        }

        @TestMetadata("WhenEntry.kt")
        public void testWhenEntry() throws Exception {
            String fileName = JetTestUtils.navigationMetadata("idea/testData/joinLines/removeBraces/WhenEntry.kt");
            doTest(fileName);
        }

        @TestMetadata("While.kt")
        public void testWhile() throws Exception {
            String fileName = JetTestUtils.navigationMetadata("idea/testData/joinLines/removeBraces/While.kt");
            doTest(fileName);
        }
    }
}