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

package android.bluetooth;

import android.annotation.SdkConstant;
import android.annotation.SdkConstant.SdkConstantType;
<<<<<<< HEAD
import android.content.Context;
=======
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
>>>>>>> 6457d361a7e38464d2679a053e8b417123e00c6a
import android.os.IBinder;
import android.os.RemoteException;
import android.os.ServiceManager;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

<<<<<<< HEAD

=======
>>>>>>> 6457d361a7e38464d2679a053e8b417123e00c6a
/**
 * This class provides the APIs to control the Bluetooth Pan
 * Profile.
 *
 *<p>BluetoothPan is a proxy object for controlling the Bluetooth
 * Service via IPC. Use {@link BluetoothAdapter#getProfileProxy} to get
 * the BluetoothPan proxy object.
 *
 *<p>Each method is protected with its appropriate permission.
 *@hide
 */
public final class BluetoothPan implements BluetoothProfile {
    private static final String TAG = "BluetoothPan";
<<<<<<< HEAD
    private static final boolean DBG = false;
=======
    private static final boolean DBG = true;
    private static final boolean VDBG = false;
>>>>>>> 6457d361a7e38464d2679a053e8b417123e00c6a

    /**
     * Intent used to broadcast the change in connection state of the Pan
     * profile.
     *
     * <p>This intent will have 4 extras:
     * <ul>
     *   <li> {@link #EXTRA_STATE} - The current state of the profile. </li>
     *   <li> {@link #EXTRA_PREVIOUS_STATE}- The previous state of the profile.</li>
     *   <li> {@link BluetoothDevice#EXTRA_DEVICE} - The remote device. </li>
     *   <li> {@link #EXTRA_LOCAL_ROLE} - Which local role the remote device is
     *   bound to. </li>
     * </ul>
     *
     * <p>{@link #EXTRA_STATE} or {@link #EXTRA_PREVIOUS_STATE} can be any of
     * {@link #STATE_DISCONNECTED}, {@link #STATE_CONNECTING},
     * {@link #STATE_CONNECTED}, {@link #STATE_DISCONNECTING}.
     *
     * <p> {@link #EXTRA_LOCAL_ROLE} can be one of {@link #LOCAL_NAP_ROLE} or
     * {@link #LOCAL_PANU_ROLE}
     * <p>Requires {@link android.Manifest.permission#BLUETOOTH} permission to
     * receive.
     */
    @SdkConstant(SdkConstantType.BROADCAST_INTENT_ACTION)
    public static final String ACTION_CONNECTION_STATE_CHANGED =
        "android.bluetooth.pan.profile.action.CONNECTION_STATE_CHANGED";

    /**
     * Extra for {@link #ACTION_CONNECTION_STATE_CHANGED} intent
     * The local role of the PAN profile that the remote device is bound to.
     * It can be one of {@link #LOCAL_NAP_ROLE} or {@link #LOCAL_PANU_ROLE}.
     */
    public static final String EXTRA_LOCAL_ROLE = "android.bluetooth.pan.extra.LOCAL_ROLE";

<<<<<<< HEAD
=======
    public static final int PAN_ROLE_NONE = 0;
>>>>>>> 6457d361a7e38464d2679a053e8b417123e00c6a
    /**
     * The local device is acting as a Network Access Point.
     */
    public static final int LOCAL_NAP_ROLE = 1;
<<<<<<< HEAD
=======
    public static final int REMOTE_NAP_ROLE = 1;
>>>>>>> 6457d361a7e38464d2679a053e8b417123e00c6a

    /**
     * The local device is acting as a PAN User.
     */
    public static final int LOCAL_PANU_ROLE = 2;
<<<<<<< HEAD
=======
    public static final int REMOTE_PANU_ROLE = 2;
>>>>>>> 6457d361a7e38464d2679a053e8b417123e00c6a

    /**
     * Return codes for the connect and disconnect Bluez / Dbus calls.
     * @hide
     */
    public static final int PAN_DISCONNECT_FAILED_NOT_CONNECTED = 1000;

    /**
     * @hide
     */
    public static final int PAN_CONNECT_FAILED_ALREADY_CONNECTED = 1001;

    /**
     * @hide
     */
    public static final int PAN_CONNECT_FAILED_ATTEMPT_FAILED = 1002;

    /**
     * @hide
     */
    public static final int PAN_OPERATION_GENERIC_FAILURE = 1003;

    /**
     * @hide
     */
    public static final int PAN_OPERATION_SUCCESS = 1004;

<<<<<<< HEAD
    private ServiceListener mServiceListener;
    private BluetoothAdapter mAdapter;
    private IBluetooth mService;
=======
    private Context mContext;
    private ServiceListener mServiceListener;
    private BluetoothAdapter mAdapter;
    private IBluetoothPan mPanService;
>>>>>>> 6457d361a7e38464d2679a053e8b417123e00c6a

    /**
     * Create a BluetoothPan proxy object for interacting with the local
     * Bluetooth Service which handles the Pan profile
     *
     */
<<<<<<< HEAD
    /*package*/ BluetoothPan(Context mContext, ServiceListener l) {
        IBinder b = ServiceManager.getService(BluetoothAdapter.BLUETOOTH_SERVICE);
        mServiceListener = l;
        mAdapter = BluetoothAdapter.getDefaultAdapter();
        if (b != null) {
            mService = IBluetooth.Stub.asInterface(b);
            if (mServiceListener != null) {
                mServiceListener.onServiceConnected(BluetoothProfile.PAN, this);
            }
        } else {
            Log.w(TAG, "Bluetooth Service not available!");

            // Instead of throwing an exception which prevents people from going
            // into Wireless settings in the emulator. Let it crash later when it is actually used.
            mService = null;
        }
    }

    /*package*/ void close() {
        mServiceListener = null;
    }

=======
    /*package*/ BluetoothPan(Context context, ServiceListener l) {
        mContext = context;
        mServiceListener = l;
        mAdapter = BluetoothAdapter.getDefaultAdapter();
        try {
            mAdapter.getBluetoothManager().registerStateChangeCallback(mStateChangeCallback);
        } catch (RemoteException re) {
            Log.w(TAG,"Unable to register BluetoothStateChangeCallback",re);
        }
        Log.d(TAG, "BluetoothPan() call bindService");
        if (!context.bindService(new Intent(IBluetoothPan.class.getName()),
                                 mConnection, 0)) {
            Log.e(TAG, "Could not bind to Bluetooth HID Service");
        }
        Log.d(TAG, "BluetoothPan(), bindService called");
    }

    /*package*/ void close() {
        if (VDBG) log("close()");
        if (mConnection != null) {
            mContext.unbindService(mConnection);
            mConnection = null;
        }
        mServiceListener = null;
        try {
            mAdapter.getBluetoothManager().unregisterStateChangeCallback(mStateChangeCallback);
        } catch (RemoteException re) {
            Log.w(TAG,"Unable to register BluetoothStateChangeCallback",re);
        }
    }

    protected void finalize() {
        close();
    }

    private IBluetoothStateChangeCallback mStateChangeCallback = new IBluetoothStateChangeCallback.Stub() {

        @Override
        public void onBluetoothStateChange(boolean on) throws RemoteException {
            //Handle enable request to bind again.
            if (on) {
                Log.d(TAG, "onBluetoothStateChange(on) call bindService");
                if (!mContext.bindService(new Intent(IBluetoothPan.class.getName()),
                                     mConnection, 0)) {
                    Log.e(TAG, "Could not bind to Bluetooth HID Service");
                }
                Log.d(TAG, "BluetoothPan(), bindService called");
            } else {
                if (VDBG) Log.d(TAG,"Unbinding service...");
                synchronized (mConnection) {
                    try {
                        mPanService = null;
                        mContext.unbindService(mConnection);
                    } catch (Exception re) {
                        Log.e(TAG,"",re);
                    }
                }
            }
        }
    };

>>>>>>> 6457d361a7e38464d2679a053e8b417123e00c6a
    /**
     * Initiate connection to a profile of the remote bluetooth device.
     *
     * <p> This API returns false in scenarios like the profile on the
     * device is already connected or Bluetooth is not turned on.
     * When this API returns true, it is guaranteed that
     * connection state intent for the profile will be broadcasted with
     * the state. Users can get the connection state of the profile
     * from this intent.
     *
     * <p>Requires {@link android.Manifest.permission#BLUETOOTH_ADMIN}
     * permission.
     *
     * @param device Remote Bluetooth Device
     * @return false on immediate error,
     *               true otherwise
     * @hide
     */
    public boolean connect(BluetoothDevice device) {
        if (DBG) log("connect(" + device + ")");
<<<<<<< HEAD
        if (mService != null && isEnabled() &&
            isValidDevice(device)) {
            try {
                return mService.connectPanDevice(device);
=======
        if (mPanService != null && isEnabled() &&
            isValidDevice(device)) {
            try {
                return mPanService.connect(device);
>>>>>>> 6457d361a7e38464d2679a053e8b417123e00c6a
            } catch (RemoteException e) {
                Log.e(TAG, "Stack:" + Log.getStackTraceString(new Throwable()));
                return false;
            }
        }
<<<<<<< HEAD
        if (mService == null) Log.w(TAG, "Proxy not attached to service");
=======
        if (mPanService == null) Log.w(TAG, "Proxy not attached to service");
>>>>>>> 6457d361a7e38464d2679a053e8b417123e00c6a
        return false;
    }

    /**
     * Initiate disconnection from a profile
     *
     * <p> This API will return false in scenarios like the profile on the
     * Bluetooth device is not in connected state etc. When this API returns,
     * true, it is guaranteed that the connection state change
     * intent will be broadcasted with the state. Users can get the
     * disconnection state of the profile from this intent.
     *
     * <p> If the disconnection is initiated by a remote device, the state
     * will transition from {@link #STATE_CONNECTED} to
     * {@link #STATE_DISCONNECTED}. If the disconnect is initiated by the
     * host (local) device the state will transition from
     * {@link #STATE_CONNECTED} to state {@link #STATE_DISCONNECTING} to
     * state {@link #STATE_DISCONNECTED}. The transition to
     * {@link #STATE_DISCONNECTING} can be used to distinguish between the
     * two scenarios.
     *
     * <p>Requires {@link android.Manifest.permission#BLUETOOTH_ADMIN}
     * permission.
     *
     * @param device Remote Bluetooth Device
     * @return false on immediate error,
     *               true otherwise
     * @hide
     */
    public boolean disconnect(BluetoothDevice device) {
        if (DBG) log("disconnect(" + device + ")");
<<<<<<< HEAD
        if (mService != null && isEnabled() &&
            isValidDevice(device)) {
            try {
                return mService.disconnectPanDevice(device);
=======
        if (mPanService != null && isEnabled() &&
            isValidDevice(device)) {
            try {
                return mPanService.disconnect(device);
>>>>>>> 6457d361a7e38464d2679a053e8b417123e00c6a
            } catch (RemoteException e) {
                Log.e(TAG, "Stack:" + Log.getStackTraceString(new Throwable()));
                return false;
            }
        }
<<<<<<< HEAD
        if (mService == null) Log.w(TAG, "Proxy not attached to service");
=======
        if (mPanService == null) Log.w(TAG, "Proxy not attached to service");
>>>>>>> 6457d361a7e38464d2679a053e8b417123e00c6a
        return false;
    }

    /**
     * {@inheritDoc}
     */
    public List<BluetoothDevice> getConnectedDevices() {
<<<<<<< HEAD
        if (DBG) log("getConnectedDevices()");
        if (mService != null && isEnabled()) {
            try {
                return mService.getConnectedPanDevices();
=======
        if (VDBG) log("getConnectedDevices()");
        if (mPanService != null && isEnabled()) {
            try {
                return mPanService.getConnectedDevices();
>>>>>>> 6457d361a7e38464d2679a053e8b417123e00c6a
            } catch (RemoteException e) {
                Log.e(TAG, "Stack:" + Log.getStackTraceString(new Throwable()));
                return new ArrayList<BluetoothDevice>();
            }
        }
<<<<<<< HEAD
        if (mService == null) Log.w(TAG, "Proxy not attached to service");
=======
        if (mPanService == null) Log.w(TAG, "Proxy not attached to service");
>>>>>>> 6457d361a7e38464d2679a053e8b417123e00c6a
        return new ArrayList<BluetoothDevice>();
    }

    /**
     * {@inheritDoc}
     */
    public List<BluetoothDevice> getDevicesMatchingConnectionStates(int[] states) {
<<<<<<< HEAD
        if (DBG) log("getDevicesMatchingStates()");
        if (mService != null && isEnabled()) {
            try {
                return mService.getPanDevicesMatchingConnectionStates(states);
=======
        if (VDBG) log("getDevicesMatchingStates()");
        if (mPanService != null && isEnabled()) {
            try {
                return mPanService.getDevicesMatchingConnectionStates(states);
>>>>>>> 6457d361a7e38464d2679a053e8b417123e00c6a
            } catch (RemoteException e) {
                Log.e(TAG, "Stack:" + Log.getStackTraceString(new Throwable()));
                return new ArrayList<BluetoothDevice>();
            }
        }
<<<<<<< HEAD
        if (mService == null) Log.w(TAG, "Proxy not attached to service");
=======
        if (mPanService == null) Log.w(TAG, "Proxy not attached to service");
>>>>>>> 6457d361a7e38464d2679a053e8b417123e00c6a
        return new ArrayList<BluetoothDevice>();
    }

    /**
     * {@inheritDoc}
     */
    public int getConnectionState(BluetoothDevice device) {
<<<<<<< HEAD
        if (DBG) log("getState(" + device + ")");
        if (mService != null && isEnabled()
            && isValidDevice(device)) {
            try {
                return mService.getPanDeviceConnectionState(device);
=======
        if (VDBG) log("getState(" + device + ")");
        if (mPanService != null && isEnabled()
            && isValidDevice(device)) {
            try {
                return mPanService.getConnectionState(device);
>>>>>>> 6457d361a7e38464d2679a053e8b417123e00c6a
            } catch (RemoteException e) {
                Log.e(TAG, "Stack:" + Log.getStackTraceString(new Throwable()));
                return BluetoothProfile.STATE_DISCONNECTED;
            }
        }
<<<<<<< HEAD
        if (mService == null) Log.w(TAG, "Proxy not attached to service");
=======
        if (mPanService == null) Log.w(TAG, "Proxy not attached to service");
>>>>>>> 6457d361a7e38464d2679a053e8b417123e00c6a
        return BluetoothProfile.STATE_DISCONNECTED;
    }

    public void setBluetoothTethering(boolean value) {
        if (DBG) log("setBluetoothTethering(" + value + ")");
        try {
<<<<<<< HEAD
            mService.setBluetoothTethering(value);
=======
            mPanService.setBluetoothTethering(value);
>>>>>>> 6457d361a7e38464d2679a053e8b417123e00c6a
        } catch (RemoteException e) {
            Log.e(TAG, "Stack:" + Log.getStackTraceString(new Throwable()));
        }
    }

    public boolean isTetheringOn() {
<<<<<<< HEAD
        if (DBG) log("isTetheringOn()");
        try {
            return mService.isTetheringOn();
        } catch (RemoteException e) {
            Log.e(TAG, "Stack:" + Log.getStackTraceString(new Throwable()));
            return false;
        }
    }

=======
        if (VDBG) log("isTetheringOn()");
        try {
            return mPanService.isTetheringOn();
        } catch (RemoteException e) {
            Log.e(TAG, "Stack:" + Log.getStackTraceString(new Throwable()));
        }
        return false;
    }

    private ServiceConnection mConnection = new ServiceConnection() {
        public void onServiceConnected(ComponentName className, IBinder service) {
            if (DBG) Log.d(TAG, "BluetoothPAN Proxy object connected");
            mPanService = IBluetoothPan.Stub.asInterface(service);

            if (mServiceListener != null) {
                mServiceListener.onServiceConnected(BluetoothProfile.PAN,
                                                    BluetoothPan.this);
            }
        }
        public void onServiceDisconnected(ComponentName className) {
            if (DBG) Log.d(TAG, "BluetoothPAN Proxy object disconnected");
            mPanService = null;
            if (mServiceListener != null) {
                mServiceListener.onServiceDisconnected(BluetoothProfile.PAN);
            }
        }
    };

>>>>>>> 6457d361a7e38464d2679a053e8b417123e00c6a
    private boolean isEnabled() {
       if (mAdapter.getState() == BluetoothAdapter.STATE_ON) return true;
       return false;
    }

    private boolean isValidDevice(BluetoothDevice device) {
       if (device == null) return false;

       if (BluetoothAdapter.checkBluetoothAddress(device.getAddress())) return true;
       return false;
    }

    private static void log(String msg) {
      Log.d(TAG, msg);
    }
}
