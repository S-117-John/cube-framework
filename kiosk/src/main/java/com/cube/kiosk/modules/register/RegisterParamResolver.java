package com.cube.kiosk.modules.register;

import com.cube.kiosk.modules.common.model.RequestData;
import com.cube.kiosk.modules.register.model.RegisterParam;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import org.dom4j.*;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

@Component
public class RegisterParamResolver {

    public RegisterParam getParam(RegisterParam registerParam){
        if(!StringUtils.isEmpty(registerParam.getHardParam())){
            Document doc = null;
            try {
                doc = DocumentHelper.parseText(registerParam.getHardParam());
            } catch (DocumentException e) {

            }
            Element root = doc.getRootElement();// 指向根节点
            Iterator it = root.elementIterator();
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

                                if("IDNAME".equalsIgnoreCase(value)){
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
                                        registerParam.setBirthday(simpleDateFormat1.format(date));
                                    } catch (ParseException e) {
                                        registerParam.setBirthday("");
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
        }

        return registerParam;
    }




}
