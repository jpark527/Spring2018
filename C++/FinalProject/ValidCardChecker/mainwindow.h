#ifndef MAINWINDOW_H
#define MAINWINDOW_H
#include <QMainWindow>
#include <map>
#include <QString>
#include "database.h"

// Fix database file path HERE!!
#define VISA_PATH "/Users/j/QtStuff/CS3A/visaConverted.dat"
#define MC_PATH "/Users/j/QtStuff/CS3A/mcConverted.dat"
#define AMEX_PATH "/Users/j/QtStuff/CS3A/amexConverted.dat"

namespace Ui {
class MainWindow;
}

class MainWindow : public QMainWindow {
    Q_OBJECT

public:
    explicit MainWindow(QWidget *parent = 0);
    ~MainWindow();

private:
    Ui::MainWindow *ui;
    std::map<int, QString> data;
    void setupSignalsSlots();
    void getDataFromConvertedDataFiles();
    void generateDisplayText(QString& text) const;
    bool luhnAlgorithm(const QString &input) const;

protected:
    void changeEvent(QEvent *e);

public slots:
    void validateCard();
    void changeDisplayText(QString data);
};

#endif // MAINWINDOW_H
