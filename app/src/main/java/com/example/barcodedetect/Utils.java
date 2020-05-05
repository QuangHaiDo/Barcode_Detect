/*
 * Copyright 2019 Google LLC
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.example.barcodedetect;

import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;

import androidx.core.app.ActivityCompat;

import java.util.ArrayList;
import java.util.List;



import static androidx.core.content.ContextCompat.checkSelfPermission;

/** Utility class to provide helper methods. */
public class Utils {

  /**
   * If the absolute difference between aspect ratios is less than this tolerance, they are
   * considered to be the same aspect ratio.
   */
  public static final float ASPECT_RATIO_TOLERANCE = 0.01f;

    public static void requestRuntimePermissions(Activity activity) {
    List<String> allNeededPermissions = new ArrayList<>();
    for (String permission : getRequiredPermissions(activity)) {
      if (checkSelfPermission(activity, permission) != PackageManager.PERMISSION_GRANTED) {
        allNeededPermissions.add(permission);
      }
    }

    if (!allNeededPermissions.isEmpty()) {
      ActivityCompat.requestPermissions(
          activity, allNeededPermissions.toArray(new String[0]), /* requestCode= */ 0);
    }
  }

  public static boolean allPermissionsGranted(Context context) {
    for (String permission : getRequiredPermissions(context)) {
      if (checkSelfPermission(context, permission) != PackageManager.PERMISSION_GRANTED) {
        return false;
      }
    }
    return true;
  }

  private static String[] getRequiredPermissions(Context context) {
    try {
      PackageInfo info =
          context
              .getPackageManager()
              .getPackageInfo(context.getPackageName(), PackageManager.GET_PERMISSIONS);
      String[] ps = info.requestedPermissions;
      return (ps != null && ps.length > 0) ? ps : new String[0];
    } catch (Exception e) {
      return new String[0];
    }
  }

}
