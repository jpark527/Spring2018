#include "mainwindow.h"
#include "ui_mainwindow.h"

#define CARD_DEGIT 16

MainWindow::MainWindow(QWidget *parent) :
    QMainWindow(parent),
    ui(new Ui::MainWindow) {
    ui->setupUi(this);
    getDataFromConvertedDataFiles();
    setupSignalsSlots();
}

void MainWindow::changeEvent(QEvent *e) {
    QMainWindow::changeEvent(e);
    switch (e->type()) {
    case QEvent::LanguageChange:
        ui->retranslateUi(this);
        break;
    default:
        break;
    }
}

void MainWindow::getDataFromConvertedDataFiles() {
    Database db;
    db.load(AMEX_PATH);
    db.load(MC_PATH);
    db.load(VISA_PATH);
    data = db.getData();
}

void MainWindow::setupSignalsSlots() {
    connect(ui->ValidateButton, SIGNAL(released()), this, SLOT(validateCard()));
}

void MainWindow::changeDisplayText(QString data) {
    ui->DisplayMessage->setText(data);
}

void MainWindow::validateCard() {
    QString text(ui->CardTypeEdit->text());
    generateDisplayText(text);
    ui->DisplayMessage->setText(text);
    ui->DisplayMessage->repaint();
}

void MainWindow::generateDisplayText(QString& text) const {
    QString newText = "";
    int length = text.length() < 6 ? text.length() : 6;
    for(int i=0; i<length; ++i)
        newText+=text.at(i);
    if(luhnAlgorithm(text) && data.find(newText.toInt())->second!="") {
        text = data.find(newText.toInt())->second;
    } else
        text = "Card number invalid.";
}

bool MainWindow::luhnAlgorithm(const QString& input) const {
    int count=0;
    long number=input.trimmed().toLong(), modBy=10, sum=number % modBy, num=number / modBy;
    while(num && count < CARD_DEGIT) {
        long digit = num % modBy;
        if(!(count%2))
            digit *= 2;
        if(digit>9)
            digit -= 9;
        sum += digit;
        num /= modBy;
        ++count;
    }
    return number && count==CARD_DEGIT-1 && !(sum % 10);
}

MainWindow::~MainWindow() {
    delete ui;
}
