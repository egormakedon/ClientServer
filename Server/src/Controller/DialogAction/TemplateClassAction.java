package Controller.DialogAction;

import Model.*;

import java.util.ArrayList;

public class TemplateClassAction {
    public ArrayList run(ArrayList<String> arrayList, int num, Model model) {
        ArrayList newList = new ArrayList();

        if (num == 1) {
            if (!checkString(arrayList.get(0))) {
                arrayList.clear();
                arrayList.add("2");
                return arrayList;
            }

            newList = searchData1(arrayList, model);
        }

        if (num == 2) {
            if (!checkString(arrayList.get(1))) {
                arrayList.clear();
                arrayList.add("2");
                return arrayList;
            }

            newList = searchData2(arrayList, model);
        }

        if (num == 3) {
            if (!checkString(arrayList.get(1))) {
                arrayList.clear();
                arrayList.add("2");
                return arrayList;
            }

            newList = searchData3(arrayList, model);
        }

        return newList;
    }

    private ArrayList searchData1(ArrayList<String> arrayList, Model model) {
        ArrayList newList = new ArrayList();

        for (Person person : model.getPersons()) {
            if (person.getHouse() == Integer.parseInt(arrayList.get(0)) && person.getSurname().equals(arrayList.get(1))) {
                newList.add(person);
            }
        }

        if (newList.size() == 0) newList.add("3");
        return newList;
    }

    private ArrayList searchData2(ArrayList<String> arrayList, Model model) {
       ArrayList newList = new ArrayList();

       for (Person person : model.getPersons()) {
           if (person.getStreet().equals(arrayList.get(0)) && person.getApartment() == Integer.parseInt(arrayList.get(1))) {
               newList.add(person);
           }
       }

       if (newList.size() == 0) newList.add("3");
       return newList;
    }

    private ArrayList searchData3(ArrayList<String> arrayList, Model model) {
        ArrayList newList = new ArrayList();

        for (Person person : model.getPersons()) {
            if (person.getSurname().equals(arrayList.get(0))) {
                int kolNum = 0;
                String houseNum = Integer.toString(person.getHouse());

                for (int i = 0; i < arrayList.get(1).length(); i++) {
                    if (!(houseNum.indexOf(arrayList.get(1).charAt(i)) == -1)) kolNum++;
                }

                if (kolNum == arrayList.get(1).length()) {
                    newList.add(person);
                }
            }
        }

        if (newList.size() == 0) newList.add("3");
        return newList;
    }

    public ArrayList<String> checkSameData(ArrayList<String> arrayList) {
        for (String string : arrayList) {
            if (string.equals("")) {
                arrayList.clear();
                arrayList.add("1");
                return arrayList;
            }
        }

        return arrayList;
    }

    private boolean checkString(String string) {
        try {
            Integer.parseInt(string);
        } catch (Exception e) {
            return false;
        }
        return true;
    }
}