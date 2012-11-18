/*
<<<<<<< HEAD
 * Copyright (C) 2008 The Android Open Source Project
=======
 * Copyright (C) 2012 The Android Open Source Project
>>>>>>> 6457d361a7e38464d2679a053e8b417123e00c6a
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

package android.telephony;

import android.os.Parcel;
import android.os.Parcelable;

/**
<<<<<<< HEAD
 * Represent one snapshot observation of one cell info
 * which contains the time of observation.
 *
 * @hide Pending API review
 */
public final class CellInfo implements Parcelable {
    // Type to distinguish where time stamp gets recorded.
    public static final int CELL_INFO_TIMESTAMP_TYPE_UNKNOWN = 0;
    public static final int CELL_INFO_TIMESTAMP_TYPE_ANTENNA = 1;
    public static final int CELL_INFO_TIMESTAMP_TYPE_MODEM = 2;
    public static final int CELL_INFO_TIMESTAMP_TYPE_OEM_RIL = 3;
    public static final int CELL_INFO_TIMESTAMP_TYPE_JAVA_RIL = 4;

    // Observation time stamped as type in nanoseconds since boot
    private final long mTimeStamp;
    // Where time stamp gets recorded.
    // Value of CELL_INFO_TIMESTAMP_TYPE_XXXX
    private final int mTimeStampType;

    private final boolean mRegistered;

    private final SignalStrength mStrength;
    private final long mTimingAdvance;

    private final int mCellIdentityType;
    private final CellIdentity mCellIdentity;

    /**
     * Public constructor
     * @param timeStampType is one of CELL_INFO_TIMESTAMP_TYPE_XXXX
     * @param timeStamp is observation time in nanoseconds since boot
     * @param timingAdv is observed timing advance
     * @param registered is true when register to this cellIdentity
     * @param strength is observed signal strength
     * @param cellIdentity is observed mobile cell
     */
    public CellInfo(int timeStampType, long timeStamp, long timingAdv,
            boolean registered, SignalStrength strength,
            CellIdentity cellIdentity) {

        if (timeStampType < CELL_INFO_TIMESTAMP_TYPE_UNKNOWN ||
                timeStampType > CELL_INFO_TIMESTAMP_TYPE_JAVA_RIL) {
            mTimeStampType = CELL_INFO_TIMESTAMP_TYPE_UNKNOWN;
        } else {
            mTimeStampType = timeStampType;
        }

        mRegistered = registered;
        mTimeStamp = timeStamp;
        mTimingAdvance = timingAdv;
        mStrength = new SignalStrength(strength);

        mCellIdentityType = cellIdentity.getCellIdType();
        // TODO: make defense copy
        mCellIdentity = cellIdentity;
    }

    public CellInfo(CellInfo ci) {
        this.mTimeStampType = ci.mTimeStampType;
        this.mRegistered = ci.mRegistered;
        this.mTimeStamp = ci.mTimeStamp;
        this.mTimingAdvance = ci.mTimingAdvance;
        this.mCellIdentityType = ci.mCellIdentityType;
        this.mStrength = new SignalStrength(ci.mStrength);
        switch(mCellIdentityType) {
            case CellIdentity.CELLID_TYPE_GSM:
                mCellIdentity = new GsmCellIdentity((GsmCellIdentity)ci.mCellIdentity);
                break;
            default:
                mCellIdentity = null;
        }
    }

    private CellInfo(Parcel in) {
        mTimeStampType = in.readInt();
        mRegistered = (in.readInt() == 1) ? true : false;
        mTimeStamp = in.readLong();
        mTimingAdvance = in.readLong();
        mCellIdentityType = in.readInt();
        mStrength = SignalStrength.CREATOR.createFromParcel(in);
        switch(mCellIdentityType) {
            case CellIdentity.CELLID_TYPE_GSM:
                mCellIdentity = GsmCellIdentity.CREATOR.createFromParcel(in);
                break;
            default:
                mCellIdentity = null;
        }
    }

    /**
     * @return the observation time in nanoseconds since boot
     */
    public long getTimeStamp() {
        return mTimeStamp;
    }

    /**
     * @return Where time stamp gets recorded.
     * one of CELL_INFO_TIMESTAMP_TYPE_XXXX
=======
 * Immutable cell information from a point in time.
 */
public abstract class CellInfo implements Parcelable {

    // Type fields for parceling
    /** @hide */
    protected static final int TYPE_GSM = 1;
    /** @hide */
    protected static final int TYPE_CDMA = 2;
    /** @hide */
    protected static final int TYPE_LTE = 3;

    // Type to distinguish where time stamp gets recorded.

    /** @hide */
    public static final int TIMESTAMP_TYPE_UNKNOWN = 0;
    /** @hide */
    public static final int TIMESTAMP_TYPE_ANTENNA = 1;
    /** @hide */
    public static final int TIMESTAMP_TYPE_MODEM = 2;
    /** @hide */
    public static final int TIMESTAMP_TYPE_OEM_RIL = 3;
    /** @hide */
    public static final int TIMESTAMP_TYPE_JAVA_RIL = 4;

    // True if device is mRegistered to the mobile network
    private boolean mRegistered;

    // Observation time stamped as type in nanoseconds since boot
    private long mTimeStamp;

    // Where time stamp gets recorded.
    // Value of TIMESTAMP_TYPE_XXXX
    private int mTimeStampType;

    /** @hide */
    protected CellInfo() {
        this.mRegistered = false;
        this.mTimeStampType = TIMESTAMP_TYPE_UNKNOWN;
        this.mTimeStamp = Long.MAX_VALUE;
    }

    /** @hide */
    protected CellInfo(CellInfo ci) {
        this.mRegistered = ci.mRegistered;
        this.mTimeStampType = ci.mTimeStampType;
        this.mTimeStamp = ci.mTimeStamp;
    }

    /** True if this cell is registered to the mobile network */
    public boolean isRegistered() {
        return mRegistered;
    }
    /** @hide */
    public void setRegisterd(boolean registered) {
        mRegistered = registered;
    }

    /** Approximate time of this cell information in nanos since boot */
    public long getTimeStamp() {
        return mTimeStamp;
    }
    /** @hide */
    public void setTimeStamp(long timeStamp) {
        mTimeStamp = timeStamp;
    }

    /**
     * Where time stamp gets recorded.
     * @return one of TIMESTAMP_TYPE_XXXX
     *
     * @hide
>>>>>>> 6457d361a7e38464d2679a053e8b417123e00c6a
     */
    public int getTimeStampType() {
        return mTimeStampType;
    }
<<<<<<< HEAD

    /**
     * @return true when register to this cellIdentity
     */
    public boolean isRegistered() {
        return mRegistered;
    }

    /**
     * @return observed timing advance
     */
    public long getTimingAdvance() {
        return mTimingAdvance;
    }

    /**
     * @return observed signal strength
     */
    public SignalStrength getSignalStrength() {
        // make a defense copy
        return new SignalStrength(mStrength);
    }

    /**
     * @return observed cell identity
     */
    public CellIdentity getCellIdentity() {
        // TODO: make a defense copy
        return mCellIdentity;
    }

    @Override
    public String toString() {
        StringBuffer sb = new StringBuffer();

        sb.append("TimeStampType: ");
        switch(mTimeStampType) {
            case 1:
                sb.append("antenna");
                break;
            case 2:
                sb.append("modem");
                break;
            case 3:
                sb.append("oem_ril");
                break;
            case 4:
                sb.append("java_ril");
                break;
            default:
                sb.append("unknown");
        }
        sb.append(", TimeStamp: ").append(mTimeStamp).append(" ns");
        sb.append(", Registered: ").append(mRegistered ? "YES" : "NO");
        sb.append(", TimingAdvance: ").append(mTimingAdvance);
        sb.append(", Strength : " + mStrength);
        sb.append(", Cell Iden: " + mCellIdentity);
=======
    /** @hide */
    public void setTimeStampType(int timeStampType) {
        if (timeStampType < TIMESTAMP_TYPE_UNKNOWN || timeStampType > TIMESTAMP_TYPE_JAVA_RIL) {
            mTimeStampType = TIMESTAMP_TYPE_UNKNOWN;
        } else {
            mTimeStampType = timeStampType;
        }
    }

    @Override
    public int hashCode() {
        int primeNum = 31;
        return ((mRegistered ? 0 : 1) * primeNum) + ((int)(mTimeStamp / 1000) * primeNum)
                + (mTimeStampType * primeNum);
    }

    @Override
    public boolean equals(Object other) {
        if (other == null) {
            return false;
        }
        if (this == other) {
            return true;
        }
        try {
            CellInfo o = (CellInfo) other;
            return mRegistered == o.mRegistered
                    && mTimeStamp == o.mTimeStamp && mTimeStampType == o.mTimeStampType;
        } catch (ClassCastException e) {
            return false;
        }
    }

    private static String timeStampTypeToString(int type) {
        switch (type) {
            case 1:
                return "antenna";
            case 2:
                return "modem";
            case 3:
                return "oem_ril";
            case 4:
                return "java_ril";
            default:
                return "unknown";
        }
    }

    @Override
    public String toString() {
        StringBuffer sb = new StringBuffer();
        String timeStampType;

        sb.append(" mRegistered=").append(mRegistered ? "YES" : "NO");
        timeStampType = timeStampTypeToString(mTimeStampType);
        sb.append(" mTimeStampType=").append(timeStampType);
        sb.append(" mTimeStamp=").append(mTimeStamp).append("ns");
>>>>>>> 6457d361a7e38464d2679a053e8b417123e00c6a

        return sb.toString();
    }

<<<<<<< HEAD
    /** Implement the Parcelable interface {@hide} */
=======
    /**
     * Implement the Parcelable interface
     */
>>>>>>> 6457d361a7e38464d2679a053e8b417123e00c6a
    @Override
    public int describeContents() {
        return 0;
    }

<<<<<<< HEAD
    /** Implement the Parcelable interface {@hide} */
    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(mTimeStampType);
        dest.writeInt(mRegistered ? 1 : 0);
        dest.writeLong(mTimeStamp);
        dest.writeLong(mTimingAdvance);
        dest.writeInt(mCellIdentityType);
        mStrength.writeToParcel(dest, flags);
        mCellIdentity.writeToParcel(dest, flags);
    }

    /** Implement the Parcelable interface {@hide} */
    public static final Creator<CellInfo> CREATOR =
            new Creator<CellInfo>() {
        @Override
        public CellInfo createFromParcel(Parcel in) {
            return new CellInfo(in);
=======
    /** Implement the Parcelable interface */
    @Override
    public abstract void writeToParcel(Parcel dest, int flags);

    /**
     * Used by child classes for parceling.
     *
     * @hide
     */
    protected void writeToParcel(Parcel dest, int flags, int type) {
        dest.writeInt(type);
        dest.writeInt(mRegistered ? 1 : 0);
        dest.writeInt(mTimeStampType);
        dest.writeLong(mTimeStamp);
    }

    /**
     * Used by child classes for parceling
     *
     * @hide
     */
    protected CellInfo(Parcel in) {
        mRegistered = (in.readInt() == 1) ? true : false;
        mTimeStampType = in.readInt();
        mTimeStamp = in.readLong();
    }

    /** Implement the Parcelable interface */
    public static final Creator<CellInfo> CREATOR = new Creator<CellInfo>() {
        @Override
        public CellInfo createFromParcel(Parcel in) {
                int type = in.readInt();
                switch (type) {
                    case TYPE_GSM: return CellInfoGsm.createFromParcelBody(in);
                    case TYPE_CDMA: return CellInfoCdma.createFromParcelBody(in);
                    case TYPE_LTE: return CellInfoLte.createFromParcelBody(in);
                    default: throw new RuntimeException("Bad CellInfo Parcel");
                }
>>>>>>> 6457d361a7e38464d2679a053e8b417123e00c6a
        }

        @Override
        public CellInfo[] newArray(int size) {
            return new CellInfo[size];
        }
    };
}
