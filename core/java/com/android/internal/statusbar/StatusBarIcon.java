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

package com.android.internal.statusbar;

import android.os.Parcel;
import android.os.Parcelable;
<<<<<<< HEAD

public class StatusBarIcon implements Parcelable {
    public String iconPackage;
=======
import android.os.UserHandle;

public class StatusBarIcon implements Parcelable {
    public String iconPackage;
    public UserHandle user;
>>>>>>> 6457d361a7e38464d2679a053e8b417123e00c6a
    public int iconId;
    public int iconLevel;
    public boolean visible = true;
    public int number;
    public CharSequence contentDescription;

<<<<<<< HEAD
    public StatusBarIcon(String iconPackage, int iconId, int iconLevel, int number,
            CharSequence contentDescription) {
        this.iconPackage = iconPackage;
=======
    public StatusBarIcon(String iconPackage, UserHandle user, int iconId, int iconLevel, int number,
            CharSequence contentDescription) {
        this.iconPackage = iconPackage;
        this.user = user;
>>>>>>> 6457d361a7e38464d2679a053e8b417123e00c6a
        this.iconId = iconId;
        this.iconLevel = iconLevel;
        this.number = number;
        this.contentDescription = contentDescription;
    }

    @Override
    public String toString() {
<<<<<<< HEAD
        return "StatusBarIcon(pkg=" + this.iconPackage + " id=0x" + Integer.toHexString(this.iconId)
=======
        return "StatusBarIcon(pkg=" + this.iconPackage + "user=" + user.getIdentifier()
                + " id=0x" + Integer.toHexString(this.iconId)
>>>>>>> 6457d361a7e38464d2679a053e8b417123e00c6a
                + " level=" + this.iconLevel + " visible=" + visible
                + " num=" + this.number + " )";
    }

    @Override
    public StatusBarIcon clone() {
<<<<<<< HEAD
        StatusBarIcon that = new StatusBarIcon(this.iconPackage, this.iconId, this.iconLevel,
                this.number, this.contentDescription);
=======
        StatusBarIcon that = new StatusBarIcon(this.iconPackage, this.user, this.iconId,
                this.iconLevel, this.number, this.contentDescription);
>>>>>>> 6457d361a7e38464d2679a053e8b417123e00c6a
        that.visible = this.visible;
        return that;
    }

    /**
     * Unflatten the StatusBarIcon from a parcel.
     */
    public StatusBarIcon(Parcel in) {
        readFromParcel(in);
    }

    public void readFromParcel(Parcel in) {
        this.iconPackage = in.readString();
<<<<<<< HEAD
=======
        this.user = (UserHandle) in.readParcelable(null);
>>>>>>> 6457d361a7e38464d2679a053e8b417123e00c6a
        this.iconId = in.readInt();
        this.iconLevel = in.readInt();
        this.visible = in.readInt() != 0;
        this.number = in.readInt();
        this.contentDescription = in.readCharSequence();
    }

    public void writeToParcel(Parcel out, int flags) {
        out.writeString(this.iconPackage);
<<<<<<< HEAD
=======
        out.writeParcelable(this.user, 0);
>>>>>>> 6457d361a7e38464d2679a053e8b417123e00c6a
        out.writeInt(this.iconId);
        out.writeInt(this.iconLevel);
        out.writeInt(this.visible ? 1 : 0);
        out.writeInt(this.number);
        out.writeCharSequence(this.contentDescription);
    }

    public int describeContents() {
        return 0;
    }

    /**
     * Parcelable.Creator that instantiates StatusBarIcon objects
     */
    public static final Parcelable.Creator<StatusBarIcon> CREATOR
            = new Parcelable.Creator<StatusBarIcon>()
    {
        public StatusBarIcon createFromParcel(Parcel parcel)
        {
            return new StatusBarIcon(parcel);
        }

        public StatusBarIcon[] newArray(int size)
        {
            return new StatusBarIcon[size];
        }
    };
}

