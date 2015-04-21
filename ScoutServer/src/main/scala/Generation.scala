/**
 * Created by mnenmenth on 3/18/15.
 */

import java.io._

import org.apache.poi.ss.usermodel._
import org.apache.poi.xssf.usermodel._

class Generation(data:String) {

  def excelGen {
    val text: String = data
    val vals = text.split("\\|")
    if(vals(0) != "Pit") {

      val allianceRaw = vals(0).split(":")
      val alliance = allianceRaw(1)
      println(alliance)
      val teamRaw = vals(1).split(":")
      val team = teamRaw(1)

      val matchNumRaw = vals(2).split(":")
      val matchNum = matchNumRaw(1)

      val autoConScoreRaw = vals(3).split(":")
      val autoConScore = autoConScoreRaw(1)

      val autoToteScoreRaw = vals(4).split(":")
      val autoToteScore = autoToteScoreRaw(1)

      val autoToteStackRaw = vals(5).split(":")
      val autoToteStack = autoToteStackRaw(1)

      val teleConNoodleRaw = vals(6).split(":")
      val teleConNoodle = teleConNoodleRaw(1)

      val teleStackHeightRaw = vals(7).split(":")
      val teleStackHeight = teleStackHeightRaw(1)

      val teleToteScoreRaw = vals(8).split(":")
      val teleToteScore = teleToteScoreRaw(1)

      val shootDoorRaw = vals(9).split(":")
      val shootDoor = shootDoorRaw(1)

      val uToteRaw = vals(10).split(":")
      val uTote = uToteRaw(1)

      val hasAutonRaw = vals(11).split(":")
      val hasAuton = hasAutonRaw(1)

      val landfillRaw = vals(12).split(":")
      val landfill = landfillRaw(1)

      val wb: Workbook = new XSSFWorkbook()
      val sheetName = "Team-" + team + "_" + "Match-" + matchNum
      //val doesExist = new File(sheetName + ".xls")
      //if (doesExist == null) {

      val sheet: Sheet = wb.createSheet(sheetName)

      val printSetup: PrintSetup = sheet.getPrintSetup
      printSetup.setLandscape(true)
      sheet.setFitToPage(true)
      sheet.setHorizontallyCenter(true)

      /*
    *
    * Title Row
    *
    */
      val titleRow: Row = sheet.createRow(0);
      titleRow.setHeightInPoints(45)

      //alliance row
      val allianceCell = titleRow.createCell(0)
      allianceCell.setCellValue("Alliance")

      //team Cell
      val teamCell = titleRow.createCell(1)
      teamCell.setCellValue("Team #")

      //match Cell
      val matchCell = titleRow.createCell(2)
      matchCell.setCellValue("Match #")

      //autoConScore
      val autoConScoreCell = titleRow.createCell(3)
      autoConScoreCell.setCellValue("Autonomous Container Score")

      //autoToteScore
      val autoToteScoreCell = titleRow.createCell(4)
      autoToteScoreCell.setCellValue("Autonomous Tote Score")

      //autoToteStack
      val autoToteStackCell = titleRow.createCell(5)
      autoToteStackCell.setCellValue("Autonomous Tote Stack")

      //teleConNoodle
      val teleConNoodleCell = titleRow.createCell(6)
      teleConNoodleCell.setCellValue("Teleop Container Noodle")

      //teleStackHeight
      val teleStackHeightCell = titleRow.createCell(7)
      teleStackHeightCell.setCellValue("Teleop Tote Stack Height")

      //teleToteScore
      val teleToteScoreCell = titleRow.createCell(8)
      teleToteScoreCell.setCellValue("Teleop Tote Score")

      //shootDoor?
      val shootDoorCell = titleRow.createCell(9)
      shootDoorCell.setCellValue("Shoot Door?")

      //uTote
      val uToteCell = titleRow.createCell(10)
      uToteCell.setCellValue("Upside Down Tote")

      //hasAuton
      val hasAutonCell = titleRow.createCell(11)
      hasAutonCell.setCellValue("Autonomous Mode")

      //landfill
      val landfillCell = titleRow.createCell(12)
      landfillCell.setCellValue("Landfill Pickup")

      /*
    *
    * Info Row
    *
    */

      val infoRow: Row = sheet.createRow(1)
      infoRow.setHeightInPoints(45)

      //alliance
      val allianceInfoCell = infoRow.createCell(0)
      allianceInfoCell.setCellValue(alliance)
      println(allianceInfoCell.getStringCellValue)

      val teamInfoCell = infoRow.createCell(1)
      teamInfoCell.setCellValue(team)

      //match Cell
      val matchInfoCell = infoRow.createCell(2)
      matchInfoCell.setCellValue(matchNum)

      //autoConScore
      val autoConScoreInfoCell = infoRow.createCell(3)
      autoConScoreInfoCell.setCellValue(autoConScore)

      //autoToteScore
      val autoToteScoreInfoCell = infoRow.createCell(4)
      autoToteScoreInfoCell.setCellValue(autoToteScore)

      //autoToteStack
      val autoToteStackInfoCell = infoRow.createCell(5)
      autoToteStackInfoCell.setCellValue(autoToteStack)

      //teleConNoodle
      val teleConNoodleInfoCell = infoRow.createCell(6)
      teleConNoodleInfoCell.setCellValue(teleConNoodle)

      //teleStackHeight
      val teleStackHeightInfoCell = infoRow.createCell(7)
      teleStackHeightInfoCell.setCellValue(teleStackHeight)

      //teleToteScore
      val teleToteScoreInfoCell = infoRow.createCell(8)
      teleToteScoreInfoCell.setCellValue(teleToteScore)

      //shootDoor?
      val shootDoorInfoCell = infoRow.createCell(9)
      shootDoorInfoCell.setCellValue(shootDoor)

      //uTote
      val uToteInfoCell = infoRow.createCell(10)
      uToteInfoCell.setCellValue(uTote)

      //hasAuton
      val hasAutonInfoCell = infoRow.createCell(11)
      hasAutonInfoCell.setCellValue(hasAuton)

      //landfill
      val landfillInfoCell = infoRow.createCell(12)
      landfillInfoCell.setCellValue(landfill)

      var i = 1
      while (i <= 12) {
        sheet.autoSizeColumn(i)
        i += 1
      }

      val file: String = "Team-" + team + "_" + "Match-" + matchNum + ".xls"

      val out: FileOutputStream = new FileOutputStream(file)
      wb.write(out)
      out.close()
      wb.close()
    }else{

      val allianceRaw = vals(1).split(":")
      val alliance = allianceRaw(2)
      println(alliance)
      val teamRaw = vals(2).split(":")
      val team = teamRaw(2)

      val matchNumRaw = vals(3).split(":")
      val matchNum = matchNumRaw(1)

      val coopRaw = vals(4).split(":")
      val coop = coopRaw(1)

      val landfillRaw = vals(5).split(":")
      val landfill = landfillRaw(1)

      val feederRaw = vals(6).split(":")
      val feeder = feederRaw(1)

      val toteRaw = vals(7).split(":")
      val tote = toteRaw(1)

      val rcRaw = vals(8).split(":")
      val rc = rcRaw(1)

      val moveRaw = vals(9).split(":")
      val move = moveRaw(1)

      val highStackRaw = vals(10).split(":")
      val highStack = highStackRaw(1)

      val capRaw = vals(11).split(":")
      val cap = capRaw(1)

      val extraInfoRaw = vals(12).split(":")
      val extraInfo = extraInfoRaw(1)

      val wb: Workbook = new XSSFWorkbook()
      val sheetName = "Team-" + team + "_" + "Match-" + matchNum

      val sheet: Sheet = wb.createSheet(sheetName)

      val printSetup: PrintSetup = sheet.getPrintSetup
      printSetup.setLandscape(true)
      sheet.setFitToPage(true)
      sheet.setHorizontallyCenter(true)

      var i = 1
      while (i <= 12) {
        sheet.autoSizeColumn(i)
        i += 1
      }

      val file: String = "Team-" + team + "_" + "Match-" + matchNum + ".xls"

      val out: FileOutputStream = new FileOutputStream(file)
      wb.write(out)
      out.close()
      wb.close()

    }
  }

  def txtGen{
    val test: String = "Alliance: Red Alliance|Team #: 245|Match #: 45|Autonomous Container Score: 3|Autonomous Tote Score: 23|Autonomous Tote Stack: 34|Teleop Container Noodle: true|Teleop Tote Score: 234|Shoot Door?: true|Upside Down Tote: true|Has Auton: true|Landfill: true"
    //val txt: String = Server.data
    val vals = test.split("\\|")
    vals.foreach{println}
    val alliance = vals(0)
    val team = vals(1)
    val matchNum = vals(2)
    val autoConScore = vals(3)
    val autoToteScore = vals(4)
    val autoToteStack = vals(5)
    val teleConNoodle = vals(6)
    val teleStackHeight = vals(6)
    val teleToteScore = vals(7)
    val shootDoor = vals(8)
    val uTote = vals(9)
    val hasAuton = vals(10)
    val landfill = vals(11)

    val writer: PrintWriter = new PrintWriter(new File(team + "-" + matchNum + ".txt"), "UTF-8")

    writer.println(alliance)
    writer.println(team)
    writer.println(matchNum)
    writer.println(autoConScore)
    writer.println(autoToteScore)
    writer.println(autoToteStack)
    writer.println(teleConNoodle)
    writer.println(teleStackHeight)
    writer.println(teleToteScore)
    writer.println(shootDoor)
    writer.println(uTote)
    writer.println(hasAuton)
    writer.println(landfill)
    writer.close

  }
}