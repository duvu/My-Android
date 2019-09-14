package mobile.myandroid.info;

import android.annotation.TargetApi;
import android.content.Context;
import android.hardware.Camera;
import android.hardware.camera2.CameraAccessException;
import android.hardware.camera2.CameraCharacteristics;
import android.hardware.camera2.CameraManager;
import android.os.Build;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import android.util.Size;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import mobile.myandroid.R;
import mobile.myandroid.util.StringTool;

/**
 * Created by beou on 26/10/2015.
 */
public class CameraInformationActivity extends AppCompatActivity {
    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_camera_information);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        List<CamInfo> listCam = null;
        listCam = getCameraInfo();

        if (listCam == null) return;

        ImageView imgCameraBack = (ImageView) findViewById(R.id.img_camera_back);

        TextView txtCameraBackDesc = (TextView) findViewById(R.id.txt_camera_back_desc);
        TextView txtCameraBackSize = (TextView) findViewById(R.id.txt_camera_back_size);
        TextView txtCameraBackSizeUnit = (TextView) findViewById(R.id.txt_camera_back_size_unit);

        ImageView imgCameraFront = (ImageView) findViewById(R.id.img_camera_front);

        TextView txtCameraFrontDesc = (TextView) findViewById(R.id.txt_camera_front_desc);
        TextView txtCameraFrontSize = (TextView) findViewById(R.id.txt_camera_front_size);
        TextView txtCameraFrontSizeUnit = (TextView) findViewById(R.id.txt_camera_front_size_unit);

        imgCameraBack.setImageDrawable(getResources().getDrawable(R.drawable.back_camera));
        imgCameraFront.setImageDrawable(getResources().getDrawable(R.drawable.front_camera_image));
        for (CamInfo camInfo : listCam) {
            if (camInfo.getCamFacing().equals("Front")) {
                txtCameraFrontDesc.setText(camInfo.getCamFacing());
                txtCameraFrontSize.setText(String.valueOf(camInfo.getCamDensity()));
                txtCameraFrontSizeUnit.setText("Megapixel");

            } else if (camInfo.getCamFacing().equals("Back")) {
                txtCameraBackDesc.setText(camInfo.getCamFacing());
                txtCameraBackSize.setText(String.valueOf(camInfo.getCamDensity()));
                txtCameraBackSizeUnit.setText("Megapixel");
            }
        }
    }
    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }
    //-- need collect: Camera Front
    //-- Camera Back
    private List<CamInfo> getCameraInfo() {
        List<CamInfo> listCam = new ArrayList<>();
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.LOLLIPOP) {
            CameraManager cameraManager = (CameraManager) getSystemService(Context.CAMERA_SERVICE);
            String[] listCameras = new String[0];
            try {
                listCameras = cameraManager.getCameraIdList();

                for (String ci : listCameras) {
                    CameraCharacteristics camChar = cameraManager.getCameraCharacteristics(ci);

                    Size pixelSize = camChar.get(CameraCharacteristics.SENSOR_INFO_PIXEL_ARRAY_SIZE);
                    int h = pixelSize.getHeight();
                    int w = pixelSize.getWidth();
                    CamInfo camInfo = null;
                    int camFacing = camChar.get(CameraCharacteristics.LENS_FACING);
                    if (camFacing == CameraCharacteristics.LENS_FACING_BACK) {
                        camInfo = new CamInfo(h, w, "Back");
                    } else if (camFacing == CameraCharacteristics.LENS_FACING_FRONT) {
                        camInfo = new CamInfo(h, w, "Front");
                    } else if (camFacing == CameraCharacteristics.LENS_FACING_EXTERNAL) {
                        camInfo = new CamInfo(h, w, "External");
                    }
                    listCam.add(camInfo);
                }

            } catch (CameraAccessException e) {
                e.printStackTrace();
            }

        } else {
            int numOfCam = Camera.getNumberOfCameras();
            try {

                for (int i = 0; i < numOfCam; i++) {
                    CamInfo camInfo = null;
                    Camera.CameraInfo cameraInfo = new Camera.CameraInfo();
                    Camera.getCameraInfo(i, cameraInfo);
                    if (cameraInfo.facing == Camera.CameraInfo.CAMERA_FACING_FRONT) {
                        Camera camera = Camera.open(Camera.CameraInfo.CAMERA_FACING_FRONT);
                        Camera.Size size = camera.getParameters().getPictureSize();
                        camInfo = new CamInfo(size.height, size.width, "Front");
                    } else if (cameraInfo.facing == Camera.CameraInfo.CAMERA_FACING_BACK) {
                        Camera camera = Camera.open(Camera.CameraInfo.CAMERA_FACING_BACK);
                        Camera.Size size = camera.getParameters().getPictureSize();
                        camInfo = new CamInfo(size.height, size.width, "Back");
                    }
                    listCam.add(camInfo);
                }
            } catch (Exception e) {
                //-- no
                Toast.makeText(this, R.string.cannot_connect_camera, Toast.LENGTH_LONG).show();
            }
        }
        return listCam;
    }

    private class CamInfo {
        int camDensity = 0;
        String camFacing;

        public CamInfo(int h, int w, String facing) {
            camDensity = (int)Math.round(StringTool.formatSizeMB(h * w));
            camFacing = facing;
        }

        public CamInfo(int camDensity, String camName) {
            this.camDensity = camDensity;
            this.camFacing = camName;
        }

        public int getCamDensity() {
            return camDensity;
        }

        public void setCamDensity(int camDensity) {
            this.camDensity = camDensity;
        }

        public String getCamFacing() {
            return camFacing;
        }

        public void setCamFacing(String camFacing) {
            this.camFacing = camFacing;
        }
    }
}
