package binggallery.chinacloudsites.cn;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.lang.reflect.Type;

import retrofit.converter.ConversionException;
import retrofit.converter.Converter;
import retrofit.mime.MimeUtil;
import retrofit.mime.TypedInput;
import retrofit.mime.TypedOutput;

/**
 * Created by liuxue on 2015/6/30.
 */
public class ImageConverter implements Converter {
    @Override
    public Object fromBody(TypedInput body, Type type) throws ConversionException {
//        String charset = this.charset;
        String charset = "UTF-8";
        if (body.mimeType() != null) {
            charset = MimeUtil.parseCharset(body.mimeType(), charset);
        }
        InputStreamReader isr = null;
        try {
            isr = new InputStreamReader(body.in(), charset);

            StringBuffer stringBuffer = new StringBuffer();
            BufferedReader br = new BufferedReader(isr);
            String line = null;
            while ((line = br.readLine()) != null) {
                stringBuffer.append(line).append("\r\n");
            }
            String str = stringBuffer.toString();

            return Image.parse(str);
        } catch (IOException e) {
            throw new ConversionException(e);
        } catch (Exception e) {
            throw new ConversionException(e);
        } finally {
            if (isr != null) {
                try {
                    isr.close();
                } catch (IOException ignored) {
                }
            }
        }
    }

    @Override
    public TypedOutput toBody(Object object) {
        return null;
    }
}
