/*
 * Copyright (C) 2008 The Android Open Source Project
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

import android.app.Notification;
import android.os.Parcel;
import android.os.Parcelable;
<<<<<<< HEAD
import android.widget.RemoteViews;

=======
import android.os.UserHandle;
>>>>>>> 6457d361a7e38464d2679a053e8b417123e00c6a

/*
boolean clearable = !n.ongoingEvent && ((notification.flags & Notification.FLAG_NO_CLEAR) == 0);


// TODO: make this restriction do something smarter like never fill
// more than two screens.  "Why would anyone need more than 80 characters." :-/
final int maxTickerLen = 80;
if (truncatedTicker != null && truncatedTicker.length() > maxTickerLen) {
    truncatedTicker = truncatedTicker.subSequence(0, maxTickerLen);
}
*/

/**
 * Class encapsulating a Notification. Sent by the NotificationManagerService to the IStatusBar (in System UI).
 */
public class StatusBarNotification implements Parcelable {
<<<<<<< HEAD
    public String pkg;
    public int id;
    public String tag;
    public int uid;
    public int initialPid;
    public Notification notification;
    public int score;
    
    public StatusBarNotification() {
    }

    public StatusBarNotification(String pkg, int id, String tag,
            int uid, int initialPid, int score, Notification notification) {
=======
    public final String pkg;
    public final int id;
    public final String tag;
    public final int uid;
    public final int initialPid;
    // TODO: make this field private and move callers to an accessor that
    // ensures sourceUser is applied.
    public final Notification notification;
    public final int score;
    public final UserHandle user;

    /** This is temporarily needed for the JB MR1 PDK. */
    @Deprecated
    public StatusBarNotification(String pkg, int id, String tag, int uid, int initialPid, int score,
            Notification notification) {
        this(pkg, id, tag, uid, initialPid, score, notification, UserHandle.OWNER);
    }

    public StatusBarNotification(String pkg, int id, String tag, int uid, int initialPid, int score,
            Notification notification, UserHandle user) {
>>>>>>> 6457d361a7e38464d2679a053e8b417123e00c6a
        if (pkg == null) throw new NullPointerException();
        if (notification == null) throw new NullPointerException();

        this.pkg = pkg;
        this.id = id;
        this.tag = tag;
        this.uid = uid;
        this.initialPid = initialPid;
        this.score = score;
        this.notification = notification;
<<<<<<< HEAD
    }

    public StatusBarNotification(Parcel in) {
        readFromParcel(in);
    }

    public void readFromParcel(Parcel in) {
=======
        this.user = user;
        this.notification.setUser(user);
    }

    public StatusBarNotification(Parcel in) {
>>>>>>> 6457d361a7e38464d2679a053e8b417123e00c6a
        this.pkg = in.readString();
        this.id = in.readInt();
        if (in.readInt() != 0) {
            this.tag = in.readString();
        } else {
            this.tag = null;
        }
        this.uid = in.readInt();
        this.initialPid = in.readInt();
        this.score = in.readInt();
        this.notification = new Notification(in);
<<<<<<< HEAD
=======
        this.user = UserHandle.readFromParcel(in);
        this.notification.setUser(user);
>>>>>>> 6457d361a7e38464d2679a053e8b417123e00c6a
    }

    public void writeToParcel(Parcel out, int flags) {
        out.writeString(this.pkg);
        out.writeInt(this.id);
        if (this.tag != null) {
            out.writeInt(1);
            out.writeString(this.tag);
        } else {
            out.writeInt(0);
        }
        out.writeInt(this.uid);
        out.writeInt(this.initialPid);
        out.writeInt(this.score);
        this.notification.writeToParcel(out, flags);
<<<<<<< HEAD
=======
        user.writeToParcel(out, flags);
>>>>>>> 6457d361a7e38464d2679a053e8b417123e00c6a
    }

    public int describeContents() {
        return 0;
    }

    public static final Parcelable.Creator<StatusBarNotification> CREATOR
            = new Parcelable.Creator<StatusBarNotification>()
    {
        public StatusBarNotification createFromParcel(Parcel parcel)
        {
            return new StatusBarNotification(parcel);
        }

        public StatusBarNotification[] newArray(int size)
        {
            return new StatusBarNotification[size];
        }
    };

<<<<<<< HEAD
    public StatusBarNotification clone() {
        return new StatusBarNotification(this.pkg, this.id, this.tag,
                this.uid, this.initialPid, this.score, this.notification.clone());
    }

    public String toString() {
        return "StatusBarNotification(pkg=" + pkg + " id=" + id + " tag=" + tag
                + " score=" + score + " notn=" + notification + ")";
=======
    @Override
    public StatusBarNotification clone() {
        return new StatusBarNotification(this.pkg, this.id, this.tag, this.uid, this.initialPid,
                this.score, this.notification.clone(), this.user);
    }

    @Override
    public String toString() {
        return "StatusBarNotification(pkg=" + pkg + " id=" + id + " tag=" + tag + " score=" + score
                + " notn=" + notification + " user=" + user + ")";
>>>>>>> 6457d361a7e38464d2679a053e8b417123e00c6a
    }

    public boolean isOngoing() {
        return (notification.flags & Notification.FLAG_ONGOING_EVENT) != 0;
    }

    public boolean isClearable() {
        return ((notification.flags & Notification.FLAG_ONGOING_EVENT) == 0)
                && ((notification.flags & Notification.FLAG_NO_CLEAR) == 0);
    }
<<<<<<< HEAD
}


=======

    /** Returns a userHandle for the instance of the app that posted this notification. */
    public int getUserId() {
        return this.user.getIdentifier();
    }
}
>>>>>>> 6457d361a7e38464d2679a053e8b417123e00c6a
