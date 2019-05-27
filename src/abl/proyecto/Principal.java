package abl.proyecto;
import java.io.*;
import java.time.LocalDate;
import java.time.Period;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import javax.swing.JOptionPane;
import abl.libreria.Cadenas;
import abl.libreria.Html;



public class Principal {

	private static final String DIRECTORIO_RAIZ = "c:/boletines/boe.es/";
	private static final String URL_BOE_BASE_FECHAS = "https://www.boe.es/boe/dias/";
	
	
	public static String fechaBoletin;  // formato dd/mm/yyyy
	
	
	public static void main(String[] args) {
		fechaBoletin = "16-05-2019";
		iniciar();

	}
		
	public static void iniciar() {  
		if(Cadenas.esFecha(fechaBoletin)  &&  fechaValida(fechaBoletin)) {
		
			creacionCarpetasFechaYpdf();
			creacionHtmlNotificaciones();
			
			List<String> listaPdfs = obtenerListaPdfs( obtenerBoletinNotificaciones());
			descargarPdfs(listaPdfs); 
		}
	}
	
	
	// Métodos para trabajar los pdfs.
	
	public static void descargarPdfs(List<String> lista) {
	// Guarda los pdfs en la carpeta pdf.
		String[]fechaArchivo = dividirFecha(fechaBoletin);
		String rutaPdf = DIRECTORIO_RAIZ + fechaArchivo[0] + "-" + fechaArchivo[1] + "-" + fechaArchivo[2] + "/pdf/";
		lista.forEach(url ->{  			
				int posicion;
				if ((posicion= url.indexOf("BOE-N")) != -1) {
					String rutaGuardar = url.substring(posicion, posicion+17 );  					
					rutaGuardar = rutaPdf + rutaGuardar + ".pdf";
					Html.getDownloadFileSSL(url, rutaGuardar); 
					// url   archivoDestino
				}
		} );
		
	}  
	
	public static String pdfMayusYextension(String pdf) {
	// Pone en mayúsculas BOE-N y tambien añade la extensión del final de la URL a los que no tienen extensión.
		String resultado= "";
		// https://www.boe.es/boe_n/dias/2019/05/15/not.php?id=     boe-n-2019-331471     ( .pdf | .php | sinExtension )  
		if(!pdf.endsWith(".php")) {
			int posBoe = pdf.indexOf("boe-"); // hasta el = incluido
			String primeraParte = pdf.substring(0, posBoe); 
			String segundaParte="";
				if( pdf.endsWith(".pdf")) 
				{
					segundaParte = pdf.substring(posBoe , pdf.indexOf(".pdf") ); 
					resultado += primeraParte;
					resultado += segundaParte.toUpperCase();
					resultado += ".pdf";  
				} else {  
					segundaParte = pdf.substring(posBoe );
					resultado += primeraParte;
					resultado += segundaParte.toUpperCase();
					resultado += "&fix_bug_chrome=foo.pdf"; 
				}
		}
		return resultado;
	}
	
	public static List<String> obtenerListaPdfs(String html) {
		// Devuelve lista de todos los pdfs en el HTML que pasamos por parámetro.
		// los pdfs están en HREF comienzan:   href="/boe ...."  pueden acabar en .pdf o NO.
		List<String> listaPdfs = new ArrayList<String>();
		html = html.replace("\'", "\"").replace(" ", "").toLowerCase();
		int posicionHref;
		String buscar = "href=\"/boe";
		while ((posicionHref= html.indexOf(buscar)) != -1) {
			String enlace = html.substring(posicionHref + buscar.length() -3 );  
			enlace = enlace.substring(0, enlace.indexOf("\""));
			if (!listaPdfs.contains(enlace))  listaPdfs.add(enlace);
			html = html.substring(posicionHref + buscar.length());
		}
		List<String> urlsPdfs = listaPdfs.stream().map( linea -> {
			linea = "https://www.boe.es/" + linea  ;
			linea = pdfMayusYextension(linea);  
			return linea;
		}).collect(Collectors.toList());
		return urlsPdfs;
	}
	
	
	//  métodos para obtener la web del apartado notificaciones.
	
		public static String obtenerBoletinNotificaciones() {
			// Devuelve html del apartado NOTIFICACIONES para la fecha dada.
			String html="";
			String numeroBoletin = buscarNumeroBoletin(Html.getHTML(accederBoletinPorFecha(fechaBoletin)));
			String[] fechaPartida = dividirFecha(fechaBoletin);
			
			String extension = fechaPartida[2] + "/" + fechaPartida[1] + "/" + fechaPartida[0]; 
			String urlNotif = URL_BOE_BASE_FECHAS + extension + "/index.php?d="+ numeroBoletin + "&s=N";
			html = Html.getHTML(urlNotif); 
			return html;
		}

		public static String buscarNumeroBoletin(String html) {
		//  Así viene el número:  </abbr> 106</h2>	
		//  Devuelve el número de boletín en formato String.
			html = html.replace("\'", "\"").replace(" ", "").toUpperCase();
			int posicionAbbr;
			String numBoe="";
			String buscar = "BOE-S-2019-";
			while ((posicionAbbr= html.indexOf(buscar)) != -1) {
				numBoe = html.substring(posicionAbbr + buscar.length());
				numBoe = numBoe.substring(0, numBoe.indexOf(":"));
				return numBoe.trim();
			}
			return numBoe;
		}
			
		public static String accederBoletinPorFecha(String fecha) {  // 1º
			// Devuelve URL para acceder a web de la fecha dada.
			String url = "";
			if(fechaValida(fecha)) {
				String[]fechaBoe = dividirFecha(fecha);
				String extension = fechaBoe[2] + "/" + fechaBoe[1] + "/" + fechaBoe[0]+"/"; // En la url las fechas se leen asi:  2019/05/15
			    url = URL_BOE_BASE_FECHAS + extension;
			}
			return url;   // si no es válida la fecha devuelve cadena vacia.
		}
		
	//metodos de fechas
		public static boolean fechaValida(String fecha) {
			// Es válida cuando está dentro de los 3 últimos meses y existe la web de esa fecha.
			// Ejemplo.  Dia 16/02/2019  su web es: https://www.boe.es/boe/dias/2019/02/16/
			boolean esValida = false;
			String[] div = dividirFecha(fecha);
			String pagina = URL_BOE_BASE_FECHAS + div[2] + "/" + div[1] + "/" + div[0];
			String paginaweb = Html.getHTML(pagina); // devuelve -1 si no es correcta la URL.
			
			LocalDate fechaParametro = LocalDate.of(Integer.parseInt(div[2]),Integer.parseInt(div[1]), Integer.parseInt(div[0]));
			LocalDate fechaHoy = LocalDate.now();
			Period periodo1 = Period.ofMonths(3);
			LocalDate fechaMaxima = fechaHoy.minus(periodo1); 

			if(!paginaweb.equals("-1") && fechaMaxima.isBefore(fechaParametro) )    return true;
			else    JOptionPane.showMessageDialog(null, "Fecha no valida. ");

			return esValida;
		}
		
		public static String[] dividirFecha(String fecha) {
			String[]fechaPartida = new String[3];
			fecha.trim(); // quitamos espacios al principio y final
			if(fecha.contains("/") || fecha.contains("-") || fecha.contains(" ") ) {
				fecha=fecha.substring(0,2)+"-"+fecha.substring(3,5)+"-"+fecha.substring(6,10);
				fechaPartida = fecha.split("-");
			}
			return fechaPartida;
		}
		
		
		
	// creación archivos y directorios
		public static void creacionDirectorioBase() {
			File archivo = new File(DIRECTORIO_RAIZ);
			archivo.mkdirs(); 
		}
		public static void creacionCarpetasFechaYpdf() {
			String[]fechaPartida = dividirFecha(fechaBoletin);
			String ruta = DIRECTORIO_RAIZ + fechaPartida[0] + "-" + fechaPartida[1] + "-" + fechaPartida[2] + "/pdf";
			File archivo = new File(ruta);
			archivo.mkdirs();
		}
		
		public static void creacionHtmlNotificaciones() {
			// creacion de archivo .html en la carpeta "FECHA" 
			String[]fechaPartida = dividirFecha(fechaBoletin);
			String rutaArchivo = DIRECTORIO_RAIZ + fechaPartida[0] + "-" + fechaPartida[1] + "-" + fechaPartida[2] + "/index.html";
			String html = obtenerBoletinNotificaciones();
			Html.saveHTML(html, rutaArchivo );
		}
		

}
