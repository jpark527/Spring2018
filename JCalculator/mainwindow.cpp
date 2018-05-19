#include "mainwindow.h"
#include "ui_mainwindow.h"

MainWindow::MainWindow(QWidget *parent) :
    QMainWindow(parent),
    ui(new Ui::MainWindow) {
    ui->setupUi(this);
    numberSaved = 0;
    op = false;
    setupSignalsSlots();
}

MainWindow::~MainWindow() {
    delete ui;
}

void MainWindow::setupSignalsSlots() {
    connect(ui->Button0, SIGNAL(released()), this, SLOT(digitPressed()));
    connect(ui->Button1, SIGNAL(released()), this, SLOT(digitPressed()));
    connect(ui->Button2, SIGNAL(released()), this, SLOT(digitPressed()));
    connect(ui->Button3, SIGNAL(released()), this, SLOT(digitPressed()));
    connect(ui->Button4, SIGNAL(released()), this, SLOT(digitPressed()));
    connect(ui->Button5, SIGNAL(released()), this, SLOT(digitPressed()));
    connect(ui->Button6, SIGNAL(released()), this, SLOT(digitPressed()));
    connect(ui->Button7, SIGNAL(released()), this, SLOT(digitPressed()));
    connect(ui->Button8, SIGNAL(released()), this, SLOT(digitPressed()));
    connect(ui->Button9, SIGNAL(released()), this, SLOT(digitPressed()));
    connect(ui->ButtonPlusMinus, SIGNAL(released()), this, SLOT(unaryOperatorPressed()));
    connect(ui->ButtonPercent, SIGNAL(released()), this, SLOT(unaryOperatorPressed()));
    connect(ui->ButtonAdd, SIGNAL(released()), this, SLOT(binaryOperatorPressed()));
    connect(ui->ButtonSubtract, SIGNAL(released()), this, SLOT(binaryOperatorPressed()));
    connect(ui->ButtonMultiply, SIGNAL(released()), this, SLOT(binaryOperatorPressed()));
    connect(ui->ButtonDivide, SIGNAL(released()), this, SLOT(binaryOperatorPressed()));
}

void MainWindow::digitPressed() {
    QPushButton* button = (QPushButton*) sender();
    bool dot = false;
    if(op) {
        ui->Display->setText("");
        op = false;
    }

    for(int i=0; i<ui->Display->text().length(); ++i)
        if(ui->Display->text().at(i) == '.') {
            dot = true;
            break;
        }
    if(dot)
        ui->Display->setText(ui->Display->text() + button->text());
    else {
        double num = (ui->Display->text() + button->text()).toDouble();
        QString number(QString::number(num, 'g', 12));
        ui->Display->setText(number);
    }
}

void MainWindow::on_ButtonDot_released() {
    QString text(ui->Display->text());
    for(int i=0; i<text.length(); ++i)
        if(text.at(i) == '.')
            return;
    ui->Display->setText(ui->Display->text() + ".");
}

void MainWindow::unaryOperatorPressed() {
    QPushButton* button = (QPushButton*) sender();
    double num = (ui->Display->text()).toDouble();
    QString number;
    if(button->text()=="+/-") {
        num *= -1;
        number = QString::number(num, 'g', 12);
        ui->Display->setText(number);
    } else {
        num *= 0.01;
        number = QString::number(num, 'g', 12);
        ui->Display->setText(number);
    }
}

void MainWindow::on_ButtonClear_released() {
    numberSaved=0.;
    ui->Display->setText("0");
    ui->ButtonAdd->setChecked(false);
    ui->ButtonSubtract->setChecked(false);
    ui->ButtonMultiply->setChecked(false);
    ui->ButtonDivide->setChecked(false);
}

void MainWindow::binaryOperatorPressed() {
    QPushButton* button = (QPushButton*) sender();
    op = true;
    if(numberSaved!=0.)
        on_ButtonEquals_released();
    button->setCheckable(true);
    button->setChecked(true);
    numberSaved = ui->Display->text().toDouble();
}

void MainWindow::on_ButtonEquals_released() {
    double num = ui->Display->text().toDouble();
    if(ui->ButtonAdd->isChecked()) {
        num = numberSaved + num;
        ui->ButtonAdd->setCheckable(false);
    } else if(ui->ButtonSubtract->isChecked()) {
        num = numberSaved - num;
        ui->ButtonSubtract->setCheckable(false);
    } else if(ui->ButtonMultiply->isChecked()) {
        num = numberSaved * num;
        ui->ButtonMultiply->setCheckable(false);
    } else if(ui->ButtonDivide->isChecked()){
        if(num == 0.)
            num = 0;
        else
            num = numberSaved / num;
        ui->ButtonDivide->setCheckable(false);
    }
    QString number(QString::number(num, 'g', 15));
    ui->Display->setText(number);
    numberSaved = 0;
}
