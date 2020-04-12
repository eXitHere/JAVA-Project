/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dfts;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import net.glxn.qrgen.QRCode;
import net.glxn.qrgen.image.ImageType;

/**
 *
 * @author Thana
 */
public class generateQRcode {

    public generateQRcode() {
    }
    
    public ByteArrayInputStream getQRCode(String txt){
        ByteArrayOutputStream out = QRCode.from(txt).to(ImageType.PNG).withSize(127, 127).stream();
        ByteArrayInputStream in = new ByteArrayInputStream(out.toByteArray());
        return in;
    }
    
}
