package com.cube.kiosk.modules.register;

import com.cube.kiosk.modules.register.model.RegisterParam;
import org.dom4j.*;
import org.springframework.stereotype.Component;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

@Component
public class RegisterParamResolver {

    public RegisterParam getParam(String param){
        Document doc = null;
        try {
            doc = DocumentHelper.parseText(param);
        } catch (DocumentException e) {
           return null;
        }
        Element root = doc.getRootElement();// 指向根节点
        Iterator it = root.elementIterator();
        RegisterParam registerParam = new RegisterParam();
        while (it.hasNext()) {
            Element element = (Element) it.next();// 一个Item节点
            if("arguments".equalsIgnoreCase(element.getName())){
                List<Element> elementList = element.elements();
                for (int i = 0; i < elementList.size(); i++) {
                    Element elementChild = elementList.get(i);
                    Attribute attribute = elementChild.attribute(0);
                    if(attribute!=null){
                        String attrName = attribute.getName();
                        if("id".equalsIgnoreCase(attrName)){
                            String value = attribute.getValue();

                            if("IDName".equalsIgnoreCase(value)){
                                String text = elementChild.getText();
                                registerParam.setName(text);
                            }
                            if("IDCardNo".equalsIgnoreCase(value)){
                                String text = elementChild.getText();
                                registerParam.setIdCard(text);
                            }


                            if("Sex".equalsIgnoreCase(value)){
                                String text = elementChild.getText();
                                registerParam.setSex(text);
                            }

                            if("Born".equalsIgnoreCase(value)){
                                String text = elementChild.getText();
                                Date date = null;
                                SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyyMMdd");
                                SimpleDateFormat simpleDateFormat1 = new SimpleDateFormat("yyyy-MM-dd");
                                try {
                                    date = simpleDateFormat.parse(text);
                                    registerParam.setSex(simpleDateFormat1.format(date));
                                } catch (ParseException e) {
                                    registerParam.setSex("");
                                }

                            }

                            if("Address".equalsIgnoreCase(value)){
                                String text = elementChild.getText();
                                registerParam.setAddress(text);
                            }




                            if("ERROR".equalsIgnoreCase(value)){
                                String text = elementChild.getText();
                                if(!"SUCCESS".equalsIgnoreCase(text)){
                                    registerParam = null;
                                    break;
                                }
                            }
                        }
                    }

                }
            }

        }
        return registerParam;
    }

}
