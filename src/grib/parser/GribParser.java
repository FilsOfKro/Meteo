package grib.parser;

import java.io.IOException;
import java.util.Date;
import java.util.List;
import java.util.NoSuchElementException;

import com.sleepycat.asm.Attribute;

import model.Grille;
import model.Prevision;
import model.PrevisionParDate;
import model.Vent;
import net.sourceforge.jgrib.GribFile;
import net.sourceforge.jgrib.GribRecord;
import net.sourceforge.jgrib.GribRecordGDS;
import net.sourceforge.jgrib.NoValidGribException;
import net.sourceforge.jgrib.NotSupportedException;
import ucar.nc2.VariableSimpleIF;
import ucar.nc2.dt.grid.GridDataset;
import ucar.nc2.grib.GdsHorizCoordSys;
import ucar.nc2.grib.collection.Grib1CollectionBuilder;
import ucar.nc2.grib.collection.Grib2Collection;
import ucar.nc2.grib.collection.GribCollectionProto.GribCollection;
import ucar.nc2.grib.grib2.Grib2DataReader2;
import ucar.nc2.grib.grib2.Grib2Gds;
import ucar.nc2.grib.grib2.Grib2Record;
import ucar.nc2.grib.grib2.Grib2RecordScanner;
import ucar.nc2.grib.grib2.Grib2Variable;
import ucar.nc2.grib.grib2.table.Grib2Table;
import ucar.unidata.io.RandomAccessFile;

/**
 * Classe pour parser un fichier Grib et le mettre au format du modèle de
 * prévision.
 * 
 * @author Sebastien Palud
 */
public class GribParser {

	/**
	 * Parse un fichier .grb ou .grb2 et retourne l'objet prévision lu si aucun problème ne
	 * se passe
	 * 
	 * @param filename
	 *          Le nom du fichier à parser
	 * @return L'objet prévision équivalent aux données présentes dans le fichier
	 *         .grb
	 * @throws NoSuchElementException
	 *           Exception JGrib
	 * @throws IOException
	 *           Exception JGrib
	 * @throws NoValidGribException
	 *           Exception JGrib
	 * @throws NotSupportedException
	 *           Exception JGrib
	 */
	public Prevision parsePrevisionFromGrib(String filename)
			throws NoSuchElementException, IOException, NoValidGribException, NotSupportedException {

		if (filename.endsWith(".grb2")) {
			return parsePrevisionFromGrib2(filename);
		}

		GribFile grb = new GribFile(filename);
		GribRecordGDS gridRecords = grb.getGrids()[0];

		Grille grille = getGrille(gridRecords);

		Prevision prevision = new Prevision(grille);

		for (int i = 1; i <= grb.getRecordCount(); i += 2) {

			GribRecord ventUGrid = grb.getRecord(i);
			GribRecord ventVGrid = grb.getRecord(i + 1);

			PrevisionParDate previsionParDate = getPrevisionParDate(prevision, ventUGrid, ventVGrid);

			prevision.addPrevision(previsionParDate);
		}

		return prevision;
	}

	public Prevision parsePrevisionFromGrib2(String filename) throws IOException {


		RandomAccessFile raf = RandomAccessFile.acquire(filename);

		Grib2RecordScanner scanner = new Grib2RecordScanner(raf);

		Grib2Record gr = scanner.next();
		GdsHorizCoordSys gds = gr.getGDS().makeHorizCoordSys();
		Grille grille = getGrille2(gds);
		Prevision prevision = new Prevision(grille);

		GridDataset griddataset = GridDataset.open(filename);
		List<VariableSimpleIF> list = griddataset.getDataVariables(); 


		ucar.nc2.Attribute uWind = null;
		ucar.nc2.Attribute vWind = null; 
		
		for (VariableSimpleIF variableSimpleIF : list) {


			for(ucar.nc2.Attribute attr : variableSimpleIF.getAttributes()) {
				//System.out.println(attr.getFullName());
				if ("u-component of wind @ Maximum wind level".equals(attr.getStringValue())) {
					uWind = variableSimpleIF.findAttributeIgnoreCase("Grib2_Parameter");
				}

				if ("v-component of wind @ Maximum wind level".equals(attr.getStringValue())) {
					vWind = variableSimpleIF.findAttributeIgnoreCase("Grib2_Parameter");
				}
			}
		}
		
		System.out.println(uWind.toString());
		System.out.println(vWind.toString());
		
		while (scanner.hasNext()) {
			gr = scanner.next();
			Date date = gr.getReferenceDate().toDate();
			PrevisionParDate ppd = prevision.buildPrevisionParDate(date);

			/*for (int i = 1; i <= grb.getRecordCount(); i += 2) {

				GribRecord ventUGrid = grb.getRecord(i);
				GribRecord ventVGrid = grb.getRecord(i + 1);

				PrevisionParDate previsionParDate = getPrevisionParDate2(prevision, ventUGrid, ventVGrid);

				prevision.addPrevision(previsionParDate);
			}*/

		}

		return prevision;
	}

	public static void main(String[] args) {
		GribParser parser = new GribParser();

		try {
			Prevision p = parser.parsePrevisionFromGrib2("gfs_4_20180318_0000_000.grb2");
			System.out.println(p.getGrille().toString());
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private Grille getGrille2(GdsHorizCoordSys gds) {
		int nbx = gds.nx;
		int nby = gds.ny;

		double lonHg = gds.startx;
		double latHg = gds.starty;

		double dx = gds.dx;
		double dy = gds.dy;

		boolean uvOrienteEst = false;

		Grille grille = new Grille(nbx, nby, lonHg, latHg, dx, dy, uvOrienteEst);

		return grille;
	}

	/**
	 * Retourne un objet Grille en lisant les paramètres de la grille du fichier
	 * .grb
	 * 
	 * @param gridRecords
	 *          L'objet GribRecordGDS récupéré par JGrib
	 * @return La grille lue dans le fichier
	 */
	private Grille getGrille(GribRecordGDS gridRecords) {
		int nbx = gridRecords.getGridNX();
		int nby = gridRecords.getGridNY();

		double lonHg = gridRecords.getGridLon1();
		double latHg = gridRecords.getGridLat1();

		double dx = gridRecords.getGridDX();
		double dy = gridRecords.getGridDY();

		boolean uvOrienteEst = gridRecords.isUVEastNorth();

		Grille grille = new Grille(nbx, nby, lonHg, latHg, dx, dy, uvOrienteEst);

		return grille;
	}

	/**
	 * Retourne un objet PrevisionParDate qui contient un tableau à deux dimensions 
	 * des vents passés en paramètre. Utilise un objet prévision pour avoir les
	 * informations de la grille
	 * 
	 * @param prevision l'objet Prevision en cours de création
	 * @param ventUGrid les vecteurs U du vent
	 * @param ventVGrid les vecteurs V du vent
	 * @return l'objet PrevisionParDate créé
	 * @throws NoValidGribException erreur JGrib
	 */
	private PrevisionParDate getPrevisionParDate(Prevision prevision, 
			GribRecord ventUGrid, GribRecord ventVGrid)
					throws NoValidGribException {
		Date date = ventUGrid.getTime().getTime();
		PrevisionParDate previsionParDate = prevision.buildPrevisionParDate(date);

		for (int y = 0; y < prevision.getGrille().getNby(); y++) {
			for (int x = 0; x < prevision.getGrille().getNbx(); x++) {

				double ventU = ventUGrid.getValue(x, y);
				double ventV = ventVGrid.getValue(x, y);

				Vent vent = new Vent(ventU, ventV);

				previsionParDate.addVent(x, y, vent);
			}
		}

		return previsionParDate;
	}

	/**
	 * Retourne un objet PrevisionParDate qui contient un tableau à deux dimensions 
	 * des vents passés en paramètre. Utilise un objet prévision pour avoir les
	 * informations de la grille
	 * 
	 * @param prevision l'objet Prevision en cours de création
	 * @param ventUGrid les vecteurs U du vent
	 * @param ventVGrid les vecteurs V du vent
	 * @return l'objet PrevisionParDate créé
	 * @throws NoValidGribException erreur JGrib
	 */
	private PrevisionParDate getPrevisionParDate2(Prevision prevision, 
			GribRecord ventUGrid, GribRecord ventVGrid)
					throws NoValidGribException {
		Date date = ventUGrid.getTime().getTime();
		PrevisionParDate previsionParDate = prevision.buildPrevisionParDate(date);

		for (int y = 0; y < prevision.getGrille().getNby(); y++) {
			for (int x = 0; x < prevision.getGrille().getNbx(); x++) {

				double ventU = ventUGrid.getValue(x, y);
				double ventV = ventVGrid.getValue(x, y);

				Vent vent = new Vent(ventU, ventV);

				previsionParDate.addVent(x, y, vent);
			}
		}

		return previsionParDate;
	}

}
