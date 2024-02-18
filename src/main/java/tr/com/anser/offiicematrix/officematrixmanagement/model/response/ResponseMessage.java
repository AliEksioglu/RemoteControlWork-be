package tr.com.anser.offiicematrix.officematrixmanagement.model.response;

import tr.com.anser.offiicematrix.officematrixmanagement.enums.RME;

import java.util.Dictionary;
import java.util.Hashtable;

public class ResponseMessage {
    private static Dictionary<RME, String> responseDictionary = new Hashtable<>();

    public static void LoadResponseMessageDictionary() {
        if (responseDictionary.size() == 0) {
            responseDictionary.put(RME.HAS_ALLREADY_WEEKNAME, "Bu tablo adı daha önce kullanılmış");
            responseDictionary.put(RME.HAS_ALLREADY_USERNAME, "Bu kullanıcı adı kayıtlı");
            responseDictionary.put(RME.HAS_ALLREAY_EMAIL, "Bu e-mail zaten kayıtlı");
            responseDictionary.put(RME.NOT_FOUND_EMAIL, "Kayıtlı e-mail bulunamadı ");
            responseDictionary.put(RME.NOT_FOUND_WEEKNAME, "Bu hafta adı bulunamadı");
            responseDictionary.put(RME.NOT_MATCH_PASSWORD, "Şifre Yanlış");
            responseDictionary.put(RME.SUCCESS_LOGIN, "Giriş Başarılı");
            responseDictionary.put(RME.SUCCESS_LOGOUT, "Çıkış Başarılı");
            responseDictionary.put(RME.SUCCESS_REGISTER, "Kullanıcı Oluşturuldu");
            responseDictionary.put(RME.SUCCESS_CREATE_WEEKDATA, "Tablo Oluşturuldu");
            responseDictionary.put(RME.SUCCESS_DELETE_WEEKDATA, "Tablo Silindi");
            responseDictionary.put(RME.SUCCESS_UPDATE, "Tablo Güncellendi");
            responseDictionary.put(RME.SUCCESS_DELETE_USER, "Kullanıcı Silindi");
            responseDictionary.put(RME.NOT_FOUND_USERNAME, "Kullanıcı Bulunamadı");
        }
    }

    public static String getRM(RME enumType) {
        return responseDictionary.get(enumType);
    }

}
