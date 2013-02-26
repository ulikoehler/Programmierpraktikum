package de.bioinformatikmuenchen.pg4.validation;

/**
 *
 *
 */
public class App {

    public static void main(String[] args) {
        String a1 = "VBSD__EFG";
        String b1 = "L_MNOPQR_";
        String c1 = "_VB_SDE_FG";
        String d1 = "LMNOP_QR__";

        String a2 = "QW_ERTA";
        String b2 = "__MORTA";
        String c2 = "QW__ERTA";
        String d2 = "_MOR__TA_";

        String a3 = "RO_FLMA_O";
        String b3 = "_GN_AALOL";
        String c3 = "ROFL_MAO";
        String d3 = "GNAALO_L";
        
        String a4 = "IU58G89GF89EGF98WGEAGFGA0WEFEIWFGAW89EF34WURBF8GHFE8FA8GWE8FAWEFGEW389GWEFGWAEUIF8W93RGAGFUAW8E9G23QGRFGFGAW9F98FP93GRA9F___________________";
        String b4 = "EARIGHẞ98AW4G9ẞ8W3ZTGTẞW89GWEUGA78WRFWQ3UFG8OWGERFQA3WTRF78WTEGFUWEGFPWAEFZ98AWTGFWEUIGPA8WEZGTAWE89GWSEOFTAWE89TFWEAFGWE8FTWAE8FTAWEFIWGEFG";
        String c4 = "IU58G89GF89EGF98WGEAGFGA0WEFEIWFGAW89EF34WURBF8GHFE8FA8GWE8FAWEFGEW389GWEFGWAEUIF8W93RGAGFUAW8E9G23QGRFGFGAW9F98FP93GRA9F___________________";
        String d4 = "EARIGHẞ98AW4G9ẞ8W3ZTGTẞW89GWEUGA78WRFWQ3UFG8OWGERFQA3WTRF78WTEGFUWEGFPWAEFZ98AWTGFWEUIGPA8WEZGTAWE89GWSEOFTAWE89TFWEAFGWE8FTWAE8FTAWEFIWGEFG";

        System.out.println(a4.length());
        System.out.println(b4.length());
        System.out.println(c4.length());
        System.out.println(d4.length());
                
        AliValiAlg test = new AliValiAlg(a4, b4, c4, d4);

        System.out.println(test.getCover());

    }
}
