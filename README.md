# TTYDTattleTool
A tool to automatically print tattle data based off of Hero Mode's tattle format

See Hero Mode: https://github.com/Reeds-github/Hero-Mode 

UPDATED v1.0 (11/07/24): First Release

=========LATEST USAGE==========

Requirements: An updated version of Java with Developer Features
https://www.oracle.com/java/technologies/downloads/
and a spreadsheet editor such as excel

1: Download the lastet release

2: Should Contain: Run.bat, TTYDTattleTool.jar, and an input folder with a spreadsheet inside.

3: Create an "output" folder in the root of the folder.

4: Paste your game's global.txt in the input folder.

5: Edit Tattle Data.xlsx in a spreadsheet editor, adding to the available fields.
      Please Note:
        -Entry Name is mandatory and the program will fail to list the tattle if left empty, note that these are case-sensitive to the game.
        -Both btl and menu entries for enemies must be added to the spreadsheet, the program doesn't add skipped entries automatically

6: Run Run.bat

7: The new modified global.txt should be in the output folder.
