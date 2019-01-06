#include "mainwindow.h"
#include "ui_mainwindow.h"

MainWindow::MainWindow(QWidget *parent) :
    QMainWindow(parent),
    ui(new Ui::MainWindow) {
    ui->setupUi(this);
    initialize();
    connectSignalsSlots();
}

void MainWindow::connectSignalsSlots() {
    connect(ui->exitButton, SIGNAL(pressed()), this, SLOT(close()));
    connect(ui->saveButton,SIGNAL(pressed()), this,SLOT(saveData()));
    connect(ui->clearButton,SIGNAL(pressed()), this,SLOT(clearData()));
    connect(ui->generateButton,SIGNAL(pressed()), this,SLOT(generateData()));
}

void MainWindow::initialize() {
    row=0;
    setBankName();
    setTableWidget();
}

void MainWindow::setTableWidget() {
    QStringList title;
    title << "Card Number" << "Bank Name" << "Card Type";
    ui->displayTable->horizontalHeader()->setSectionResizeMode(QHeaderView::Stretch);
    ui->displayTable->verticalHeader()->setVisible(false);
    ui->displayTable->setRowCount(10);
    ui->displayTable->setColumnCount(3);
    ui->displayTable->setHorizontalHeaderLabels(title);
}

void MainWindow::setBankName() {
    vector<QString> bankNames;
    QStringList bankList;
    generator.getBankNames(bankNames);
    for(QString data : bankNames)
        bankList.append(data);
    bankList.sort(Qt::CaseSensitivity::CaseInsensitive);
    bankList.removeDuplicates();
    ui->chooseBankName->addItems(bankList);
}

void MainWindow::saveData() {
    QString fileName = QFileDialog::getSaveFileName(NULL, "Save File", NULL , "*.dat");
    if(fileName.isNull())
        return;
    if (QFileInfo(fileName).suffix().isEmpty())
      fileName.append(".dat");
    ofstream out(fileName.toStdString());
    for(auto& data : generatedData)
        out << data.toStdString() << '\n';
    out.close();
}

void MainWindow::clearData() {
    ui->displayTable->clearContents();
    ui->displayTable->setRowCount(10);
    ui->displayTable->repaint();
    generatedData.clear();
    row=0;
}

void MainWindow::generateData() {
    int howManyTimes = ui->howManyTextEdit->toPlainText().toInt();
    QString bankName = ui->chooseBankName->currentText(),
            cardType = ui->chooseCardType->currentText();
    for(int i=0; i<howManyTimes; ++i) {
        QString newNumber = generator.generate(bankName, cardType);
        if(!newNumber.isEmpty()) {
            ui->displayTable->setItem(row, 0, new QTableWidgetItem(newNumber));
            ui->displayTable->setItem(row, 1, new QTableWidgetItem(bankName));
            ui->displayTable->setItem(row++, 2, new QTableWidgetItem(cardType));
            ui->displayTable->repaint();
            generatedData.push_back(newNumber + " " + bankName + " " + cardType);
        }
        if(row >= ui->displayTable->rowCount())
            ui->displayTable->insertRow(ui->displayTable->rowCount());
    }
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

MainWindow::~MainWindow() {
    delete ui;
}
