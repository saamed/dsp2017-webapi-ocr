using System.IO;
using System.Text.RegularExpressions;

namespace DSP.ImageConverter
{
  class Program
  {
    static void Main(string[] args)
    {
      if (args.Length != 1)
        return;

      var path = args[0];
      

      var filePaths = Directory.GetFiles(path);

      foreach (var filePath in filePaths)
      {
        var fileName = Path.GetFileNameWithoutExtension(filePath);
        if (Regex.IsMatch(fileName, "._binarized"))
          continue;
        var newFilePath = Path.Combine(path, fileName + "_binarized" + Path.GetExtension(filePath));

        ImageConverterHelper.ConvertToBinary(filePath,newFilePath, 130);
      }
    }
  }
}
