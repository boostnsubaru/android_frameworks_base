/*
 * Copyright (C) 2010 The Android Open Source Project
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

package com.android.test.hwui;

import android.app.Activity;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.os.Bundle;
<<<<<<< HEAD
import android.view.View;
=======
import android.text.TextUtils;
import android.view.View;
import android.widget.TextView;
>>>>>>> 6457d361a7e38464d2679a053e8b417123e00c6a

@SuppressWarnings({"UnusedDeclaration"})
public class TextFadeActivity extends Activity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.text_fade);
<<<<<<< HEAD
=======
        
        findViewById(R.id.contact_tile_name).setHorizontalFadingEdgeEnabled(true);
        ((TextView) findViewById(R.id.contact_tile_name)).setEllipsize(TextUtils.TruncateAt.MARQUEE);
>>>>>>> 6457d361a7e38464d2679a053e8b417123e00c6a
    }
}
