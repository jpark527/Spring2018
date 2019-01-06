#ifndef MAINWINDOW_H
#define MAINWINDOW_H

#include <QMainWindow>

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
    double numberSaved;
    bool op;
    void setupSignalsSlots();

private slots:
    void digitPressed();
    void on_ButtonDot_released();
    void unaryOperatorPressed();
    void on_ButtonClear_released();
    void binaryOperatorPressed();
    void on_ButtonEquals_released();
};

#endif // MAINWINDOW_H
