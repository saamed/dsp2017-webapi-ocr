using Emgu.CV;
using Emgu.CV.CvEnum;
using Emgu.CV.Structure;

namespace DSP.ImageConverter
{
  public static class ImageConverterHelper
  {
    public static void ConvertToBinary(string filepath, string destination, double threshold = 150)
    {
      var img = CvInvoke.Imread(filepath);
      var dst = new Mat();
      CvInvoke.CvtColor(img, dst, ColorConversion.Bgr2Gray); // konwersja do szarości

      // binaryzacja obrazu
      var image = new Image<Gray, byte>(dst.Bitmap).ThresholdBinary(new Gray(threshold), new Gray(255));
        
      image.Save(destination);
    }
  }
}