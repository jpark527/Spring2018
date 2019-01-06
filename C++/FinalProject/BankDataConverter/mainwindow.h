#ifndef MAINWINDOW_H
#define MAINWINDOW_H
#include <QMainWindow>
#include <QString>
#include <QFile>
#include <QFileDialog>
#include <map>
#include <fstream>
#include "converter.h"

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
    Ui::MainWindow *ui;
    QString fileContents;
    std::map<int, QString> bankData;
    void printConvertedData();
    void connectSignalsSlots();
    void readFile(const QString &fileName);
    void saveFile(const QString &fileName);

private slots:
    void openInputFile();
    void openSaveFile();
    void on_convertButton_released();
};

#endif // MAINWINDOW_H
