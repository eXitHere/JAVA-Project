package dfts;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import net.glxn.qrgen.QRCode;
import net.glxn.qrgen.image.ImageType;

public class generateQRcode {

    public generateQRcode() {
    }
    
    public ByteArrayInputStream getQRCode(String txt){
        ByteArrayOutputStream out = QRCode.from(txt).to(ImageType.PNG).withSize(127, 127).stream();
        ByteArrayInputStream in = new ByteArrayInputStream(out.toByteArray());
        return in;
    }
    
}
