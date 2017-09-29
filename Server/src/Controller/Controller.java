package Controller;

import Controller.DialogAction.AddDialogAction;
import Controller.DialogAction.EraseDialogAction;
import Controller.DialogAction.SearchDialogAction;
import Model.*;

import java.util.ArrayList;

public class Controller {
    private Model model;

    public void setModel(Model model) {
        this.model = model;
    }

    public ArrayList runAddAction(ArrayList<String> arrayList) {
        AddDialogAction addDialogAction = new AddDialogAction();
        addDialogAction.setModel(model);

        ArrayList list = addDialogAction.run(arrayList);
        ArrayList newList = new ArrayList();

        if (list.size() > 10) {
            for (int i = 0; i < 10; i++) newList.add(list.get(i));
        }

        else newList = list;

        return newList;
    }

    public ArrayList runSearchAction(ArrayList<String> arrayList, int num) {
        SearchDialogAction searchDialogAction = new SearchDialogAction();
        searchDialogAction.setModel(model);

        ArrayList list = searchDialogAction.run(arrayList, num);
        ArrayList newList = new ArrayList();

        if (list.size() == 1 && (list.get(0).toString().equals("1") || list.get(0).toString().equals("2") || list.get(0).toString().equals("3"))) {
            newList = list;
            model.clearSearchList();
        }

        else {
            model.setSearchList(list);

            if (list.size() > 10) {
                for (int i = 0; i < 10; i++) newList.add(list.get(i));
            }

            else newList = list;
        }

        return newList;
    }

    public ArrayList runEraseAction(ArrayList<String> arrayList, int num) {
        EraseDialogAction eraseDialogAction = new EraseDialogAction();
        eraseDialogAction.setModel(model);

        ArrayList list = eraseDialogAction.run(arrayList, num);

        if (!((list.get(0).equals("1") || list.get(0).equals("2") || list.get(0).equals("3")) && list.size() == 1)) {
            if (model.getPersons().size() == 0) {
                ArrayList result = new ArrayList();
                result.add("-1");
                result.add(list.size());

                return result;
            }

            else {
                ArrayList newList = new ArrayList();

                if (model.getPersons().size() > 10) {
                    for (int i = 0; i < 10; i++) newList.add(model.getPersons().get(i));
                }

                newList.add(list.size());

                return newList;
            }
        }

        return list;
    }

    public int getTotalRecords(String string) {
        int totRec = 0;

        if (string.equals("frame")) totRec = model.getPersons().size();
        else if (string.equals("searchDialog")) totRec = model.getSearchList().size();

        return totRec;
    }

    public ArrayList getListOfData(int firstIndex, int lastIndex, String parentName) {
        ArrayList arrayList = new ArrayList();

        if (parentName.equals("frame")) {
            for (int i = firstIndex; i < lastIndex; i++) {
                arrayList.add(model.getPersons().get(i));
            }
        }

        else if (parentName.equals("searchDialog")) {
            for (int i = firstIndex; i < lastIndex; i++) {
                arrayList.add(model.getSearchList().get(i));
            }
        }

        return arrayList;
    }

    public String inRequest(String fileName) {
        model.saveFile(fileName);

        return "Файл сохранен";
    }

    public ArrayList outRequest(String fileName) {
        ArrayList arrayList = new ArrayList();

        model.openFile(fileName);

        if (model.getPersons().size() > 10) {
            for (int i = 0; i < 10; i++) arrayList.add(model.getPersons().get(i));
        }

        else {
            for (Person person : model.getPersons()) {
                arrayList.add(person);
            }
        }

        return arrayList;
    }

    public void clearSearchList() { model.clearSearchList(); }
}