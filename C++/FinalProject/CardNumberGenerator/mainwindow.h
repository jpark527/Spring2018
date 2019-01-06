#ifndef MAINWINDOW_H
#define MAINWINDOW_H

#include <QMainWindow>
#include <QFileDialog>
#include <fstream>
#include <map>
#include <vector>
#include "generator.h"

using namespace std;

namespace Ui {
class MainWindow;
}

class MainWindow : public QMainWindow {
    Q_OBJECT

public:
    explicit MainWindow(QWidget *parent = 0);
    ~MainWindow();

protected:
    void changeEvent(QEvent *e);

private:
    int row;
    QString bankName;
    Generator generator;
    vector<QString> generatedData;
    Ui::MainWindow *ui;
    void connectSignalsSlots();
    void initialize();
    void setBankName();
//    void setCardType();
    void setTableWidget();

private slots:
    void saveData();
    void clearData();
    void generateData();
};

#endif // MAINWINDOW_H
