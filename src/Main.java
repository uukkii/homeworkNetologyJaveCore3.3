import java.io.*;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

public class Main {
    public static void main(String[] args) {

        openZip("Games/BestGame/savegames/saves.zip", "Games/BestGame/savegames/");

        System.out.println(openProgress("Games/BestGame/savegames/save1.dat"));
        System.out.println(openProgress("Games/BestGame/savegames/save2.dat"));
        System.out.println(openProgress("Games/BestGame/savegames/save3.dat"));
    }

    public static void openZip(String dir, String dirEx) {
        try (ZipInputStream zis = new ZipInputStream(new FileInputStream(dir))) {
            ZipEntry entry;
            String dr;
            while ((entry = zis.getNextEntry()) != null) {
                dr = entry.getName();
                FileOutputStream fos = new FileOutputStream(dirEx + dr);
                for (int i = zis.read(); i != -1; i = zis.read()) {
                    fos.write(i);
                }
                fos.flush();
                fos.close();
                zis.closeEntry();
            }
        } catch (IOException ex) {
            System.err.println(ex.getMessage());
        }
    }

    public static GameProgress openProgress(String dir) {
        GameProgress gameProgress = null;
        try (FileInputStream fis = new FileInputStream(dir);
             ObjectInputStream ois = new ObjectInputStream(fis)) {
            gameProgress = (GameProgress) ois.readObject();
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }
        return gameProgress;
    }
}