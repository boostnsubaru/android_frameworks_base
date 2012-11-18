/*
 * Copyright (C) 2012 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.android.rs.test;

import android.content.Context;
import android.content.res.Resources;
import android.renderscript.*;

public class UT_math_conformance extends UnitTest {
    private Resources mRes;

    protected UT_math_conformance(RSTestCore rstc, Resources res, Context ctx) {
        super(rstc, "Math Conformance", ctx);
        mRes = res;
    }

    public void run() {
        RenderScript pRS = RenderScript.create(mCtx);
        ScriptC_math_conformance s =
<<<<<<< HEAD
                new ScriptC_math_conformance(pRS, mRes, R.raw.math_conformance);
=======
                new ScriptC_math_conformance(pRS);
>>>>>>> 6457d361a7e38464d2679a053e8b417123e00c6a
        pRS.setMessageHandler(mRsMessage);
        s.invoke_math_conformance_test();
        pRS.finish();
        waitForMessage();
        pRS.destroy();
        passTest();
    }
}
